package com.inzami.fp.rest.controller;

import com.inzami.fp.domain.Member;
import com.inzami.fp.repository.MemberRepository;
import com.inzami.fp.rest.dto.JsonResponse;
import com.inzami.fp.rest.dto.update.MemberUpdateDTO;
import com.inzami.fp.rest.dto.view.MemberViewDTO;
import com.inzami.fp.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/member")
@Slf4j
@Api(description = "Operations with members")
public class MembersController {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberService memberService;
    @Autowired
    private MapperFacade mapperFacade;

    @GetMapping("/{clientId}")
    @ApiOperation(value = "Find members for client")
    public String findByClient(@PathVariable Long clientId, ModelMap model) {
        List<Member> members = memberRepository.findByClientId(clientId);
        List<MemberViewDTO> memberViewDTOS = mapperFacade.mapAsList(members, MemberViewDTO.class);
        model.addAttribute("memberList", memberViewDTOS);
        model.addAttribute("clientId", clientId);
        return "modal/membersEdit :: content";
    }

    @PostMapping("/{clientId}")
    @ApiOperation(value = "Save members")
    @ResponseBody
    public JsonResponse<Boolean> saveMembers(@PathVariable Long clientId, @RequestBody List<MemberUpdateDTO> memberViewDTOS){
        memberService.saveMembers(clientId, memberViewDTOS);
        return JsonResponse.success(Boolean.TRUE);
    }
}
