package kr.co.yoomin.myrestfulservice.controller;

import jakarta.validation.Valid;
import kr.co.yoomin.myrestfulservice.bean.Post;
import kr.co.yoomin.myrestfulservice.bean.User;
import kr.co.yoomin.myrestfulservice.exception.UserNotFoundException;
import kr.co.yoomin.myrestfulservice.repository.PostRepository;
import kr.co.yoomin.myrestfulservice.repository.UserRepository;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.swing.text.html.Option;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/jpa")
public class UserJpaController {

    private UserRepository userRepository;
    private PostRepository postRepository;
    public UserJpaController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // /jpa/users
    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity retrieveUserById(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()){
            throw new UserNotFoundException("id -" + id);
        }
        EntityModel entityModel = EntityModel.of(user.get());
        WebMvcLinkBuilder linTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(linTo.withRel("all-users"));
        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUserById(@PathVariable int id){
        userRepository.deleteById(id);
    }

    @PostMapping("/users")
    public ResponseEntity<User> creatUser(@Valid @RequestBody User user) {
        User saveUser = userRepository.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saveUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/users/{id}/posts")
    public List<Post> retrieveAllPostsByUsre(@PathVariable int id){
        Optional<User> user=userRepository.findById(id);
        if(!user.isPresent()){
            throw  new UserNotFoundException("id - "+id);
        }
        return user.get().getPosts();
    }

    @PostMapping("/users/{id}/posts")
    public ResponseEntity<Object> createPost(@PathVariable int id, @RequestBody Post post){
        Optional<User> userOptional = userRepository.findById(id);
        if(!userOptional.isPresent()){
            throw  new UserNotFoundException("id - "+id);
        }
        User user = userOptional.get();
        post.setUser(user);
        postRepository.save(post);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
