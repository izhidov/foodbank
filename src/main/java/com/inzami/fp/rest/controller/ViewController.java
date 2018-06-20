package com.inzami.fp.rest.controller;

import com.inzami.fp.rest.dto.save.ClientSaveDTO;
import com.inzami.fp.rest.dto.save.OrganizationSaveDTO;
import com.inzami.fp.rest.dto.save.UserSaveDTO;
import com.inzami.fp.rest.dto.search.ClientSearchForm;
import com.inzami.fp.rest.dto.search.DocumentSearchForm;
import com.inzami.fp.rest.dto.search.OrganizationSearchForm;
import com.inzami.fp.rest.dto.search.UserSearchForm;
import com.inzami.fp.rest.dto.view.OrganizationSelectViewDTO;
import com.inzami.fp.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ViewController {

    @Autowired
    private OrganizationService organizationService;

    @GetMapping("/")
    public String root() {
        return "redirect:/view/client";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "/formError/access-denied";
    }

    @GetMapping("/view/client")
    public String client(Model model) {
        if (!model.containsAttribute("clientList")) {
            model.addAttribute("clientList", new ArrayList<>());
        }
        if (!model.containsAttribute("clientSearchForm")) {
            model.addAttribute("clientSearchForm", new ClientSearchForm());
        }
        if (!model.containsAttribute("clientSaveDTO")) {
            model.addAttribute("clientSaveDTO", new ClientSaveDTO());
        }
        return "client";
    }

    @GetMapping("/view/document")
    public String document(Model model) {
        if (!model.containsAttribute("documentSearchForm")) {
            model.addAttribute("documentSearchForm", new DocumentSearchForm());
        }
        return "document";
    }

    @GetMapping("/view/document/search/{number}")
    public String document(@PathVariable String number, Model model) {
        DocumentSearchForm documentSearchForm = new DocumentSearchForm();
        documentSearchForm.setDocumentNumber(number);
        model.addAttribute("documentSearchForm", documentSearchForm);
        return "/api/document/search";
    }

    @GetMapping("/view/document/list")
    public String createDocument(Model model) {
        if (!model.containsAttribute("clientEmail")) {
            model.addAttribute("clientEmail", "");
        }
        if (!model.containsAttribute("documentList")) {
            model.addAttribute("documentList", new ArrayList<>());
        }
        return "document/list";
    }

    @GetMapping("/view/user")
    public String user(Model model) {
        if (!model.containsAttribute("userList")) {
            model.addAttribute("userList", new ArrayList<>());
        }
        if (!model.containsAttribute("userSearchForm")) {
            model.addAttribute("userSearchForm", new UserSearchForm());
        }
        if (!model.containsAttribute("userSaveDTO")) {
            model.addAttribute("userSaveDTO", new UserSaveDTO());
        }
        if (!model.containsAttribute("organizations")) {
            List<OrganizationSelectViewDTO> organizationForSelect = organizationService.getOrganizationForSelect();
            model.addAttribute("organizations", organizationForSelect);
        }
        return "redirect:/api/user/search";
    }

    @GetMapping("/view/organization")
    public String organization(Model model) {
        if (!model.containsAttribute("organizationList")) {
            model.addAttribute("organizationList", new ArrayList<>());
        }
        if (!model.containsAttribute("organizationSearchForm")) {
            model.addAttribute("organizationSearchForm", new OrganizationSearchForm());
        }
        if (!model.containsAttribute("organizationSaveDTO")) {
            model.addAttribute("organizationSaveDTO", new OrganizationSaveDTO());
        }
        return "redirect:/api/organization/search";
    }
}
