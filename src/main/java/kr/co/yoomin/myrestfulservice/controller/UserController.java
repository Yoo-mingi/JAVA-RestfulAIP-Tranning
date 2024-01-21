package kr.co.yoomin.myrestfulservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.yoomin.myrestfulservice.bean.User;
import kr.co.yoomin.myrestfulservice.dao.UserDaoService;
import kr.co.yoomin.myrestfulservice.exception.UserNotFoundException;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@Tag(name="user-controller",description="일반사용자용 컨트롤러")
public class UserController {
    private UserDaoService service;

    public UserController(UserDaoService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return service.findAll();
    }

    @Operation(summary = "사용자 정보 조희 API",description = "사용자 ID를 이용해서 사용자 상세정보 조희")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "User Not found"),
            @ApiResponse(responseCode = "500", description = "Server Error")
    }

    )
    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUser(
            @Parameter (description = "사용자 ID", required = true, example = "1")
            @PathVariable int id) {
        User user = service.findOne(id);

        if(user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not foune", id));
        }
        EntityModel entityModel = EntityModel.of(user);
        WebMvcLinkBuilder linTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(linTo.withRel("all-users"));
        return entityModel;
    }

    @PostMapping("/users")
    public ResponseEntity<User> creatUser(@Valid @RequestBody User user) {
        User saveUser = service.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saveUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity deleteUser(@PathVariable int id){
        User deleteUser = service.deleteByID(id);

        if(deleteUser == null){
            throw new UserNotFoundException(String.format("ID[%s] not foune", id));
        }
        return ResponseEntity.noContent().build();
    }
}
