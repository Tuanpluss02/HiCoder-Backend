//package com.stormx.hicoder.controllers;
//
//import java.util.Arrays;
//import java.util.List;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.stormx.hicoder.controllers.requests.PostRequest;
//import com.stormx.hicoder.dto.PostDTO;
//import com.stormx.hicoder.entities.User;
//import com.stormx.hicoder.services.PostService;
//import com.stormx.hicoder.services.UserService;
//
//public class PostControllerTest {
//
//    @Mock
//    private UserService userService;
//
//    @Mock
//    private PostService postService;
//
//    @InjectMocks
//    private PostController postController;
//
//    private MockMvc mockMvc;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(postController).build();
//    }
//
//    @Test
//    public void testGetCurrentUserPosts() throws Exception {
//        // Prepare test data
//        User currentUser = new User();
//        List<PostDTO> userPosts = Arrays.asList(new PostDTO(), new PostDTO());
//
//        // Mock dependencies
//        Mockito.when(userService.getCurrentUser()).thenReturn(currentUser);
//        Mockito.when(postService.getAllPostsOfUser(currentUser)).thenReturn(userPosts);
//
//        // Perform the GET request
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/post/me"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Get user's posts successfully"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data.length()").value(userPosts.size()));
//
//        // Verify the interactions
//        Mockito.verify(userService, Mockito.times(1)).getCurrentUser();
//        Mockito.verify(postService, Mockito.times(1)).getAllPostsOfUser(currentUser);
//    }
//
//    @Test
//    public void testGetUserPostById() throws Exception {
//        // Prepare test data
//        String postId = "1";
//        User currentUser = new User();
//        PostDTO userPost = new PostDTO();
//
//        // Mock dependencies
//        Mockito.when(userService.getCurrentUser()).thenReturn(currentUser);
//        Mockito.when(postService.getUserPostById(postId, currentUser)).thenReturn(userPost);
//
//        // Perform the GET request
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/post/{postId}", postId))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Get user's posts successfully"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists());
//
//        // Verify the interactions
//        Mockito.verify(userService, Mockito.times(1)).getCurrentUser();
//        Mockito.verify(postService, Mockito.times(1)).getUserPostById(postId, currentUser);
//    }
//
//    @Test
//    public void testNewPost() throws Exception {
//        // Prepare test data
//        User currentUser = new User();
//        PostRequest postRequest = new PostRequest();
//        PostDTO createdPost = new PostDTO();
//
//        // Mock dependencies
//        Mockito.when(userService.getCurrentUser()).thenReturn(currentUser);
//        Mockito.when(postService.createPost(postRequest, currentUser)).thenReturn(createdPost);
//
//        // Perform the POST request
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/post")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(postRequest)))
//                .andExpect(MockMvcResultMatchers.status().isCreated())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Create new post successfully"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists());
//
//        // Verify the interactions
//        Mockito.verify(userService, Mockito.times(1)).getCurrentUser();
//        Mockito.verify(postService, Mockito.times(1)).createPost(postRequest, currentUser);
//    }
//
//    // Add more test methods for other controller methods
//}