package com.example.rest.service.impl;

import com.example.rest.common.CommonException;
import com.example.rest.common.CommonResponse;
import com.example.rest.common.CommonService;
import com.example.rest.common.Constant;
import com.example.rest.model.entity.File;
import com.example.rest.model.entity.Likes;
import com.example.rest.model.entity.Post;
import com.example.rest.model.entity.User;
import com.example.rest.model.mapper.PostMapper;
import com.example.rest.model.mapper.UserMapper;
import com.example.rest.model.response.post.AddPostResponse;
import com.example.rest.model.response.post.Author;
import com.example.rest.model.response.post.GetPostResponse;
import com.example.rest.model.response.posts.AuthorResponse;
import com.example.rest.model.response.posts.DataResponse;
import com.example.rest.model.response.posts.PostsResponse;
import com.example.rest.model.response.posts.VideoResponse;
import com.example.rest.repository.*;
import com.example.rest.service.IPostService;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.StringBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostService implements IPostService {
    @Autowired
    private CommonService commonService;
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private LikesRepository likesRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private FileService fileService;
    @Autowired
    private PostsRepository postsRepository;


    //t???o folder trong project ????? l??u file n???u ch??a c??
    protected static void createDirectoryIfItDoesntExist(String dir) {
        final Path path = Paths.get(dir);

        if (Files.notExists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException ie) {
                ie.getMessage();
            }
        }
    }

    @Override
    public CommonResponse<AddPostResponse> addPost(String token, MultipartFile[] image, MultipartFile video, String described, String status) throws Exception {
            //Check validate token (token b???t bu???c)
            commonService.checkCommonValidate(token);
            checkConstraintOfFile(described, image, video);
            //Save post -> Save File
            createDirectoryIfItDoesntExist(Constant.ROOT_DIRECTORY);
            //Get user Id from token
            int userId = Integer.parseInt(commonService.getUserIdFromToken(token));
            if (userId > 0) {
                Post post = postRepository.save(setCommonPostInfo(userId, described, status));
                if (post != null && post.getId() > 0) {
                    if (image.length > 0 && image.length <= 4 && image[0].getOriginalFilename().length() > 0) { //xem l???i
                        for (int i = 0; i < image.length; i++) {
                            saveFile(image[i], Paths.get(Constant.ROOT_DIRECTORY));
                            fileRepository.save(setCommonFileInfo(image[i].getOriginalFilename(), post.getId()));
                        }
                    } else if (!video.isEmpty()) {
                        saveFile(video, Paths.get(Constant.ROOT_DIRECTORY));
                        fileRepository.save(setCommonFileInfo(video.getOriginalFilename(), post.getId()));
                    }
                }
                //For response OK status
                List<AddPostResponse> list = new ArrayList<>();
                AddPostResponse addPostResponse = new AddPostResponse();
                addPostResponse.setId(String.valueOf(post.getId()));
                addPostResponse.setUrl("");
                list.add(addPostResponse);
                return new CommonResponse(Constant.OK_CODE, Constant.OK_MESSAGE, list);
            }
        throw new CommonException(Constant.COULD_NOT_PUBLISH_THIS_POST_CODE);
    }

    @Override
    public CommonResponse<GetPostResponse> getPost(String token, String postId) throws IOException, CommonException {

        CommonResponse<GetPostResponse> commonResponse = new CommonResponse();
        GetPostResponse data = new GetPostResponse();
        List<Object> res = new ArrayList<>();

        int userId = Integer.parseInt(commonService.getUserIdFromToken(token));
        if (userId < 0) {
            throw new CommonException(Constant.PARAMETER_IS_NOT_ENOUGH_CODE);
        }
        Post post = postRepository.findByUserIdAndId(userId, Integer.parseInt(postId)).orElse(null);
        if (Objects.nonNull(post)) {
            long amountLikes = likesRepository.findByPostId(postId).size();
            long amountComments = commentRepository.findByPostId(Integer.parseInt(postId)).size();
            Likes likes = likesRepository.findByUserIdAndPostId(userId, Integer.parseInt(postId));
            boolean isLike;
            if ( likes != null) {
                isLike = true;
            } else isLike = false;
            List<File> file = fileRepository.findByPostId(Integer.parseInt(postId));
            User user = userRepository.findById(post.getUserId());
            Author author = new Author();
            author.setAvatar(user.getLinkAvatar());
            author.setName(user.getName());
            author.setIsOnline(false);//hard fix

            data.setPostId(postId);
            data.setDescribed(post.getContent());
            data.setCreated(post.getCreatedDate());
            data.setModified(post.getModifiedDate());
            data.setLike(amountLikes);
            data.setComment(amountComments);
            data.setIsLiked(isLike);
//            data.setImage(file);
            data.setAuthor(author);
            data.setState(post.getState());
            if (user.getId() == userId) {
                data.setCanEdit(true);
            } else data.setCanEdit(false);
            data.setCanComment(post.getCanComment());
            res.add(data);
        }
        return new CommonResponse(Constant.OK_CODE, Constant.OK_MESSAGE, res);
    }
    @Override
    public CommonResponse deletePost(String token, String postId) throws IOException, CommonException {
        commonService.checkCommonValidate(token, postId);
        //L???y user id from token
        String userId = commonService.getUserIdFromToken(token);
        //L???y th??ng tin b??i vi???t
        Post post = postRepository.findById(Integer.parseInt(postId));
        if (post == null) {
            throw new CommonException(Constant.POST_IS_NOT_EXISTED_CODE);
        }

        if (post.getUserId() != Integer.parseInt(userId)) {
            throw new CommonException(Constant.NOT_ACCESS_CODE);
        }

        //X??a post -> isDeleted = true
        try {
            post.setDeleted(true);
            post.setModifiedDate(new Date());
            post.setModifiedBy(userId);
            postRepository.save(post);
            //X??a File c???a post
            List<File> files = fileRepository.findByPostId(post.getId());
            for (int i = 0; i < files.size(); i++) {
                File file = files.get(i);
                deleteFile(file.getContent(), Path.of(Constant.ROOT_DIRECTORY));
                file.setDeleted(true);
                file.setModifiedBy(userId);
                file.setModifiedDate(new Date());
                fileRepository.save(file);
            }
        } catch (Exception e) {
            throw new CommonException(Constant.CAN_NOT_CONNECT_TO_DB_CODE);
        }
        return new CommonResponse(Constant.OK_CODE, Constant.OK_MESSAGE, null);
    }

    /**
     * Get list-posts
     *
     * @param token
     * @param user_id
     * @param in_campaign
     * @param campaign_id
     * @param latitude
     * @param longitude
     * @param last_id
     * @param index
     * @param count
     * @return
     */
    @Override
    public CommonResponse<DataResponse> getListPosts(String token, String user_id, String in_campaign, String campaign_id, String latitude, String longitude, String last_id, String index, String count) throws CommonException {
        CommonResponse<DataResponse> commonResponse = new CommonResponse();
//        this.validateParamsGetPosts(token, user_id, in_campaign, campaign_id, latitude, longitude, last_id, index, count);

        //last_id of post not found
        if (!StringUtils.isEmpty(last_id)) {
            Post postInDB = postRepository.findById(Integer.parseInt(last_id));
            if (postInDB == null) {
                throw new CommonException(Constant.PARAMETER_VALUE_IS_INVALID_CODE);
            }
        }

        //validate token and get UserId
        int userId = Integer.parseInt(commonService.getUserIdFromToken(token));
        if (userId < 0) {
            throw new CommonException(Constant.PARAMETER_IS_NOT_ENOUGH_CODE);
        }

        if (StringUtils.isEmpty(count)) {
            count = String.valueOf(20);
        }
        if (Integer.parseInt(count) < 0) {
            throw new CommonException(Constant.PARAMETER_IS_NOT_ENOUGH_CODE);
        }
        if (StringUtils.isEmpty(index)) {
            index = String.valueOf(0);
        }
        //create datas for Client's response
        List<DataResponse> dataResponses = new ArrayList<>();

        //get files
        List<File> files = fileService.findAll();
        if (CollectionUtils.isEmpty(files)) {
            //filer file is not deleted
            files = files.stream().filter(item -> item.isDeleted() == Constant.IS_NOT_DELETED).collect(Collectors.toList());
        }

        //get authors
        List<User> usersInDB = userRepository.findAll();
        if (!CollectionUtils.isEmpty(usersInDB)) {
            //filer user is not deleted
            usersInDB = usersInDB.stream().filter(item -> item.isDeleted() == Constant.IS_NOT_DELETED).collect(Collectors.toList());
        }

        //get likes of post
        List<Likes> likesOfPost = likesRepository.findAllLikes();

        //get postsResponses
        List<Post> postsInDB = postsRepository.findPostByAll(last_id, count, index);
        //find post is not deleted
        postsInDB = postsInDB.stream().filter(item -> item.isDeleted() == Constant.IS_NOT_DELETED).collect(Collectors.toList());

        List<PostsResponse> postsResponses = new ArrayList<>();

        //convert Posts to PostResponse
        if (!CollectionUtils.isEmpty(postsInDB)) {
            for (Post post : postsInDB) {
                postsResponses.add(postMapper.toResponse(post));
            }
        }
        //add datas to PostResponses
        if (!CollectionUtils.isEmpty(postsResponses)) {
            for (PostsResponse postsResponse : postsResponses) {
                //get author by postId
                if (!CollectionUtils.isEmpty(usersInDB)) {
                    //find user by postId
                    User userInDb = usersInDB.stream().filter(item -> (item.isDeleted() == Constant.IS_NOT_DELETED)
                            && (item.getId() == Integer.parseInt(postsResponse.getUser_id()))).findAny().orElse(null);
                    if (userInDb != null) {
                        AuthorResponse authorResponse = userMapper.toResponse(userInDb);
                        authorResponse.setOnline("online");
                        //set to data return
                        postsResponse.setAuthor(authorResponse);
                    }
                }

                if (!CollectionUtils.isEmpty(files)) {
                    //get files by postId
                    List<File> fileWithPost = files.stream().filter(item -> item.isDeleted() == Constant.IS_NOT_DELETED
                            && item.getPostId() == (Integer.parseInt(postsResponse.getId()))).collect(Collectors.toList());
                    //convert file to video or images
                    if (!CollectionUtils.isEmpty(fileWithPost)) {
                        List<String> images = new ArrayList<>();
                        List<VideoResponse> videoResponses = new ArrayList<>();
                        //check all file belong to postId
                        for (File file : fileWithPost) {
                            //file is image
                            if (!StringUtils.isEmpty(file.getContent())
                                    && this.checkImageFileTypeValid(file.getContent())) {
                                images.add(file.getContent());
                                //set to data return
                                postsResponse.setImage(images);
                                postsResponse.setVideo(null);
                                //file is video
                            } else if (!StringUtils.isEmpty(file.getContent())
                                    && this.checkVideoFileTypeValid(file.getContent())) {
                                VideoResponse videoResponse = new VideoResponse();
                                videoResponse.setUrl(file.getContent());
                                videoResponse.setThumb("example thump");
                                //set to data return
                                videoResponses.add(videoResponse);
                                postsResponse.setVideo(videoResponses);
                                postsResponse.setImage(null);
                            } else {
                                //list of Files null -> both image and video are null
                                postsResponse.setImage(null);
                                postsResponse.setVideo(null);
                            }
                        }
                    }
                }
                //get likes of posts
                if (!CollectionUtils.isEmpty(likesOfPost) && !CollectionUtils.isEmpty(usersInDB)) {
                    //find list likes of posts
                    StringBuilder nameOfUserLikePost = new StringBuilder();
                    List<Likes> likesOfEachPost = likesOfPost
                            .stream()
                            .filter(item -> item.getPostId().equals(postsResponse.getId()))
                            .collect(Collectors.toList());
                    //find list users who like this post
                    for (User userLikePost : usersInDB
                    ) {
                        for (Likes likes : likesOfEachPost
                        ) {
                            if (userLikePost.getId() == likes.getUserId()) {
                                nameOfUserLikePost.append(userLikePost.getName() + ",");
                            }
                        }
                    }
                    if (!StringUtils.isEmpty(nameOfUserLikePost)) {
                        nameOfUserLikePost.deleteCharAt(nameOfUserLikePost.length() - 1);
                        postsResponse.setName(String.valueOf(nameOfUserLikePost));
                        postsResponse.setLike(String.valueOf(likesOfEachPost.size()));
                    } else {
                        postsResponse.setName(null);
                        postsResponse.setLike(String.valueOf(0));
                    }
                }
            }
        }
        DataResponse dataResponse = new DataResponse();
        dataResponse.setPosts(postsResponses);
        if (!StringUtils.isEmpty(last_id)) {
            List<Post> newPosts = postRepository.findNewPosts(last_id);
            dataResponse.setNew_items(String.valueOf(newPosts.size()));
            dataResponse.setLast_id(last_id);
        }
        dataResponses.add(dataResponse);

        //return common-response
        commonResponse.setData(dataResponses);
        commonResponse.setMessage(Constant.OK_MESSAGE);
        commonResponse.setCode(Constant.OK_CODE);
        return commonResponse;
    }

    /**
     * Validate input params
     *
     * @return: null
     */
    private CommonResponse<DataResponse> validateParamsGetPosts(String token, String user_id, String in_campaign, String campaign_id, String latitude, String longitude, String last_id, String index, String count) {

        return null;
    }

    @Override
    public CommonResponse editPost(String token, String postId, String described, String status, MultipartFile[] image, List<String> imageIdsDeleted, List<String> imageIdsSort, MultipartFile video, MultipartFile thumb, String autoAccept, String autoBlock) throws Exception {

        commonService.checkCommonValidate(token, postId);
        //L???y user id from token
        String userId = (commonService.getUserIdFromToken(token));
//        imageIdsDeleted => ph???c v??? cho case mu???n x??a ???nh
//        image && imageIdsSort => ph???c v??? cho case update
        checkConstraintOfFile(described, image, video);
        //Case Success
        //L???y th??ng tin b??i vi???t
        Post post = postRepository.findById(Integer.parseInt(postId));
        if (post == null) {
            throw new CommonException(Constant.POST_IS_NOT_EXISTED_CODE);
        }
        //L???y c??c file c???a post ????
        List<File> files = fileRepository.findByPostId(post.getId());
        if (files == null || files.size() == 0) {
            throw new CommonException(Constant.NO_DATA_OR_END_OF_LIST_DATA_CODE);
        }
        if (described.length() > 0) {
            post.setContent(described);
            post.setModifiedBy(userId);
            post.setModifiedDate(new Date());
            postRepository.save(post);
        }
        //Case x??a ???nh th??nh c??ng
        //X??a ???nh ??? b??i vi???t c?? ???nh
        if (imageIdsDeleted != null && imageIdsDeleted.size() > 0 && checkImageFileTypeValid(files.get(0).getContent())) {
            //Check c??c id ???nh mu???n x??a => ph???i tr??ng v???i c??c id ???nh c???a b??i post
            for (int i = 0; i < imageIdsDeleted.size(); i++) {
                int imageIdDeleted = Integer.parseInt(imageIdsDeleted.get(i));
                for (int j = 0; j < files.size(); j++) {
                    if (imageIdDeleted == files.get(j).getId()) {
                        File file = files.get(j);
                        commonSaveFileIntoDB(file, userId, null, "d");
                        break;
                    }
                }
            }
        } else {
            //X??a ???nh ??? b??i vi???t kh??ng c?? ???nh
            throw new CommonException(Constant.PARAMETER_VALUE_IS_INVALID_CODE);
        }
        files = fileRepository.findByPostId(Integer.parseInt(postId));
        //Case update ???nh
        //image -> nh???ng c??i file ???nh m?? mu???n thay th???
        //=> image_sort truy???n l??n th??? t??? < 0 || l???n h??n size - 1
        if (checkImageFileTypeValid(files.get(0).getContent()) && image.length > 0) {
            //C?? ???nh m???i cho s???a ???nh
            for (int i = 0; i < imageIdsSort.size(); i++) {
                int imageIndex = Integer.parseInt(imageIdsSort.get(i));
                if (imageIndex < 4 || imageIndex >= 0) {
                    //Tr??ng th?? update t??n file
                    File file = new File();
                    if (imageIndex < files.size()) {
                        file = files.get(imageIndex);
                    } else {
                        file.setPostId(Integer.parseInt(postId));
                        file.setDeleted(false);
                        file.setContent(image[i].getOriginalFilename());
                        file.setCreatedDate(new Date());
                        file.setCreatedBy(userId);
                    }
                    commonSaveFileIntoDB(file, userId, image[i], "s");

                }
            }
        } else if (checkVideoFileTypeValid(files.get(0).getContent()) && !video.isEmpty()) {
            //C?? video m???i cho s???a video
            File file = files.get(0);
            commonSaveFileIntoDB(file, userId, video, "s");
        } else {
            throw new CommonException(Constant.PARAMETER_VALUE_IS_INVALID_CODE);
        }
        return new CommonResponse(Constant.OK_CODE, Constant.OK_MESSAGE, null);
    }


    //T??nh dung l?????ng c???a m???ng image
    private long getImageFileSize(MultipartFile[] image) throws Exception {
        long imageFileS???ze = 0;
        for (MultipartFile file : image) {
            imageFileS???ze += file.getSize();
        }
        return imageFileS???ze;
    }

    //Ghi file v??o folder ???? t???o
    private CommonResponse saveFile(MultipartFile file, Path rootLocation) throws Exception {

        try (InputStream is = file.getInputStream()) {
            Files.copy(is, rootLocation.resolve(file.getOriginalFilename()));
        } catch (IOException ie) {
            throw new CommonException(Constant.UPLOAD_FILE_FAILED_CODE);

        }
        return null;
    }

    //delete file
    private CommonResponse deleteFile(String fileName, Path rootLocation) throws IOException, CommonException {
        try {
            Files.deleteIfExists(Path.of(rootLocation + "/" + fileName));
        } catch (Exception e) {
            throw new CommonException(Constant.EXCEPTION_ERROR_CODE);
        }
        return new CommonResponse(Constant.OK_CODE, Constant.OK_MESSAGE, null);
    }

    //Check ?????nh d???ng list image files ph???i d???ng .png || .jpg || .jpeg
    private boolean checkListImageFilesTypeValid(List<String> fileNames) {
        //check file type valid
        for (String fileName : fileNames) {
            if (!checkImageFileTypeValid(fileName)) {
                return false;
            }
        }
        return true;
    }

    //Check ?????nh d???ng Image File ph???i d???ng .png || .jpg || .jpeg
    private boolean checkImageFileTypeValid(String fileName) {
        int length = fileName.length();
        char[] fileChar = fileName.toCharArray();
        int indexDot = 0;
        for (int i = 0; i < fileChar.length; i++) {
            if (fileChar[i] == '.') {
                indexDot = i;
            }
        }
        String imageNameTrim = fileName.substring(indexDot + 1, length);
        return imageNameTrim.equalsIgnoreCase(Constant.PNG) || imageNameTrim.equalsIgnoreCase(Constant.JPG) || imageNameTrim.equalsIgnoreCase(Constant.JPEG);
    }

    //check ?????nh d???ng video file ph???i ??? d???ng .mp4 || .flv
    private boolean checkVideoFileTypeValid(String fileName) {
        int length = fileName.length();
        String videoNameTrim = fileName.substring(fileName.indexOf(".") + 1, length);
        return videoNameTrim.equalsIgnoreCase(Constant.MP4) || videoNameTrim.equalsIgnoreCase(Constant.FLV);
    }

    private File setCommonFileInfo(String content, int postId) {
        File file = new File();
        file.setContent(content);
        file.setCreatedDate(new Date());
        file.setDeleted(false);
        file.setPostId(postId);
        return file;
    }

    private Post setCommonPostInfo(int userId, String described, String status) {
        Post post = new Post();
        post.setCreatedBy(String.valueOf(userId));
        post.setCreatedDate(new Date());
        post.setDeleted(false);
        post.setUserId(Integer.parseInt(String.valueOf(userId)));
        post.setContent(described);
        post.setState(status);
        return post;
    }

    //C??c case r??ng bu???c chung khi upload file
    public CommonResponse checkConstraintOfFile(String described, MultipartFile[] image, MultipartFile video) throws Exception {
        //Kh??ng truy???n described, image v?? video (y/c 1 trong 3)
        if (described == null && image.length == 0 && video.isEmpty()) {
            throw new CommonException(Constant.PARAMETER_IS_NOT_ENOUGH_CODE);
        }
        //truy???n c??? image v?? video
        if ((image.length > 0) && !video.isEmpty()) {
            throw new CommonException(Constant.PARAMETER_VALUE_IS_INVALID_CODE);
        }
        //Check qu?? s??? l?????ng file
        if ((image.length > 4)) {
            throw new CommonException(Constant.MAXIMUM_NUMBER_OF_IMAGES_CODE);
        }
        //Add file name into List to trim
        if((image.length > 0 && image[0].getOriginalFilename().length() > 0) || !video.isEmpty()){
            List<String> fileNames = new ArrayList<>();
            for (int i = 0; i < image.length; i++) {
                fileNames.add(image[i].getOriginalFilename());
            }
            //truy???n image/video sai ?????nh d???ng || n???i dung b??i vi???t qu?? 500 t???
            if ((image.length > 0 && !checkListImageFilesTypeValid(fileNames)) || (!video.isEmpty() && !checkVideoFileTypeValid(video.getOriginalFilename()) || commonService.countWordInString(described) > 500)) {
                throw new CommonException(Constant.PARAMETER_VALUE_IS_INVALID_CODE);
            }
            //Check qu?? dung l?????ng c???a image v?? video
            if (getImageFileSize(image) * Constant.CONVERSION_TO_MB > 4 || video.getSize() * Constant.CONVERSION_TO_MB > 10) {
                throw new CommonException(Constant.FILE_SIZE_IS_TOO_BIG_CODE);

            }
        }
        return new CommonResponse(Constant.OK_CODE, Constant.OK_MESSAGE, null);
    }

    //function = d -> delete
    //function = s -> save
    private void commonSaveFileIntoDB(File file, String userId, MultipartFile savedFile, String function) throws Exception {
        deleteFile(file.getContent(), Path.of(Constant.ROOT_DIRECTORY));
        if (function.equalsIgnoreCase("d")) {
            file.setDeleted(true);
        } else if (function.equalsIgnoreCase("s")) {
            saveFile(savedFile, Path.of(Constant.ROOT_DIRECTORY));
            file.setContent(savedFile.getOriginalFilename());
        }
        file.setModifiedBy(userId);
        file.setModifiedDate(new Date());
        fileRepository.save(file);
    }


}
