//package com.stormx.hicoder.controllers;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.stormx.hicoder.controllers.requests.CommentRequest;
//import com.stormx.hicoder.dto.CommentDTO;
//import com.stormx.hicoder.entities.Post;
//import com.stormx.hicoder.entities.User;
//import com.stormx.hicoder.services.CommentService;
//import com.stormx.hicoder.services.PostService;
//import com.stormx.hicoder.services.UserService;
//import jakarta.servlet.RequestDispatcher;
//import jakarta.servlet.http.HttpServletRequest;
//import org.aspectj.lang.annotation.Before;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockHttpServletRequest;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
///**
// * This class is a test class for the CommentController class. It contains test
// * methods to verify the functionality of the CommentController.
// * <p>
// * To use this class, follow these steps:
// * 1. Create an instance of CommentControllerTest.
// * 2. Set up the necessary dependencies using the @Mock annotation for
// * UserService, PostService, and CommentService.
// * 3. Use the @InjectMocks annotation to inject the dependencies into the
// * CommentController instance.
// * 4. Set up the MockMvc instance using the MockMvcBuilders.standaloneSetup()
// * method, passing the CommentController instance as a parameter.
// * 5. Write test methods to test the different methods of the CommentController.
// * 6. Use the Mockito.when() method to mock the dependencies' behavior.
// * 7. Perform the HTTP requests using the mockMvc.perform() method, passing the
// * appropriate parameters.
// * 8. Use the mockMvcResultMatchers to verify the expected results of the HTTP
// * requests.
// * 9. Use the Mockito.verify() method to verify the interactions with the mocked
// * dependencies.
// * 10. Run the test methods to verify the functionality of the
// * CommentController.
// */
//@RunWith(MockitoJUnitRunner.class)
//public class CommentControllerTest {
//
//    @Mock
//    private UserService userService;
//
//    @Mock
//    private PostService postService;
//
//    @Mock
//    private CommentService commentService;
//
//    @InjectMocks
//    private CommentController commentController;
//
//    private MockMvc mockMvc;
//
//    @Before("")
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
//    }
//
//    @Test
//    public void testCreateNewComment() throws Exception {
//        // Prepare test data
//        String postId = "1";
//        CommentRequest commentRequest = new CommentRequest();
//        HttpServletRequest request = new MockHttpServletRequest();
//
//        // Mock dependencies
//        User currentUser = new User();
//        Post postToComment = new Post();
//        CommentDTO response = new CommentDTO();
//        Mockito.when(userService.getCurrentUser()).thenReturn(currentUser);
//        Mockito.when(postService.getPostById(postId)).thenReturn(postToComment);
//        Mockito.when(commentService.createComment(commentRequest, currentUser, postToComment)).thenReturn(response);
//
//        // Perform the POST request
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/post/{postId}/comment", postId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(commentRequest))
//                        .requestAttr(RequestDispatcher.ERROR_STATUS_CODE, HttpStatus.CREATED.value())
//                        .requestAttr(RequestDispatcher.ERROR_MESSAGE, "Create new comment successfully")
//                        .requestAttr(RequestDispatcher.FORWARD_REQUEST_URI, request.getRequestURI()))
//                .andExpect(MockMvcResultMatchers.status().isCreated())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Create new comment successfully"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value(response));
//
//        // Verify the interactions
//        Mockito.verify(userService, Mockito.times(1)).getCurrentUser();
//        Mockito.verify(postService, Mockito.times(1)).getPostById(postId);
//        Mockito.verify(commentService, Mockito.times(1)).createComment(commentRequest, currentUser, postToComment);
//    }
//
//    // Add more test methods for other controller methods
//
//}