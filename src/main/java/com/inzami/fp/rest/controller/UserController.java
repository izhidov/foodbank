package com.inzami.fp.rest.controller;

import com.inzami.fp.domain.User;
import com.inzami.fp.repository.UserRepository;
import com.inzami.fp.rest.dto.JsonResponse;
import com.inzami.fp.rest.dto.save.UserSaveDTO;
import com.inzami.fp.rest.dto.search.UserSearchForm;
import com.inzami.fp.rest.dto.update.UserUpdateDTO;
import com.inzami.fp.rest.dto.view.OrganizationSelectViewDTO;
import com.inzami.fp.rest.dto.view.UserViewDTO;
import com.inzami.fp.service.OrganizationService;
import com.inzami.fp.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/api/user")
@Slf4j
@Api(description = "Operations with users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MapperFacade mapperFacade;
    @Autowired
    private OrganizationService organizationService;

    @GetMapping("/search")
    @ApiOperation(value = "Find user by email")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String findByDocumentNumber(@Valid UserSearchForm userSearchForm, BindingResult bindingResult, Model model) {
        if (!model.containsAttribute("organizations")) {
            List<OrganizationSelectViewDTO> organizationForSelect = organizationService.getOrganizationForSelect();
            model.addAttribute("organizations", organizationForSelect);
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("userSearchForm", userSearchForm);
            return "user";
        }
        List<User> users = userService.searchUsers(userSearchForm);
        List<UserViewDTO> userViewDTOS = mapperFacade.mapAsList(users, UserViewDTO.class);
        model.addAttribute("userList", userViewDTOS);
        return "user";
    }

    // get by id
    @GetMapping("/{userId}")
    @ApiOperation(value = "Get by id")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseBody
    public JsonResponse<UserViewDTO> getById(@PathVariable("userId") Long userId) {
        User user = userRepository.findOne(userId);
        UserViewDTO userViewDTO = mapperFacade.map(user, UserViewDTO.class);
        return JsonResponse.success(userViewDTO);
    }

    // update
    @PostMapping("/{userId}")
    @ApiOperation(value = "Update user")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseBody
    public JsonResponse update(@PathVariable("userId") Long userId,
                               @RequestBody @Valid UserUpdateDTO userUpdateDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JsonResponse.formError(bindingResult.getAllErrors());
        }
        User user = userService.update(userId, userUpdateDTO);
        return JsonResponse.success(user.getId());
    }

    // create
    @PostMapping
    @ApiOperation(value = "Create user")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseBody
    public JsonResponse create(@RequestBody @Valid UserSaveDTO userSaveDTO, BindingResult bindingResult) {
        long count = userRepository.countByEmail(userSaveDTO.getEmail());
        if (count > 0) {
            bindingResult.rejectValue("email", "Pattern.email","User already exists");
        }
        if (bindingResult.hasErrors()) {
            return JsonResponse.formError(bindingResult.getAllErrors());
        }
        User user = userService.create(userSaveDTO);
        return JsonResponse.success(user.getId());
    }
}
