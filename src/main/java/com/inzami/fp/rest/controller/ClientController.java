package com.inzami.fp.rest.controller;

import com.inzami.fp.domain.Client;
import com.inzami.fp.repository.ClientRepository;
import com.inzami.fp.rest.dto.JsonResponse;
import com.inzami.fp.rest.dto.save.ClientSaveDTO;
import com.inzami.fp.rest.dto.search.ClientSearchForm;
import com.inzami.fp.rest.dto.update.ClientUpdateDTO;
import com.inzami.fp.rest.dto.view.ClientInfoDTO;
import com.inzami.fp.rest.dto.view.ClientViewDTO;
import com.inzami.fp.service.ClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/client")
@Slf4j
@Api(description = "Operations with clients")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ClientService clientService;
    @Autowired
    private MapperFacade mapperFacade;

    // first name autocomplete
    @GetMapping("/autocomplete/firstname")
    @ApiOperation(value = "Search first names for autocomplete")
    @PreAuthorize("isAuthenticated()")
    public JsonResponse<List<String>> searchFirstNames(@RequestParam("firstName") String firstName,
                                                       @RequestParam(value = "limit", defaultValue = "5") Integer limit) {
        Pageable pageable = new PageRequest(0, limit);
        List<String> result = clientRepository.autocompleteByFirstName(firstName, pageable);
        return JsonResponse.success(result);
    }

    // last name autocomplete
    @GetMapping("/autocomplete/lastname")
    @ApiOperation(value = "Search last names for autocomplete")
    @PreAuthorize("isAuthenticated()")
    public JsonResponse<List<String>> searchLastNames(@RequestParam("lastName") String lastName,
                                                       @RequestParam(value = "limit", defaultValue = "5") Integer limit) {
        Pageable pageable = new PageRequest(0, limit);
        List<String> result = clientRepository.autocompleteByLastName(lastName, pageable);
        return JsonResponse.success(result);
    }

    @GetMapping("/search")
    @ApiOperation(value = "Search clients")
    @PreAuthorize("isAuthenticated()")
    public String search(@Valid ClientSearchForm clientSearchForm, BindingResult bindingResult, Model model) {
        if (StringUtils.isNotBlank(clientSearchForm.getBirthDate()) && !clientSearchForm.getBirthDate().matches("\\d\\d/\\d\\d/\\d\\d\\d\\d")) {
            bindingResult.rejectValue("birthDate", "Pattern.birthDate","Format MM/dd/yyyy");
            model.addAttribute("clientSearchForm", clientSearchForm);
            return "client";
        }
        List<Client> clients = clientService.searchClients(clientSearchForm);
        List<ClientViewDTO> clientViewDTOS = mapperFacade.mapAsList(clients, ClientViewDTO.class);
        model.addAttribute("clientList", clientViewDTOS);
        return "client";
    }

    @PostMapping("/search/dialog")
    @ApiOperation(value = "Search clients for create dialog autocomplete")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public JsonResponse<List<ClientInfoDTO>> searchForDialog(@RequestParam String birthDate) throws UnsupportedEncodingException {
        birthDate = URLDecoder.decode(birthDate, "UTF8");
        if (StringUtils.isNotBlank(birthDate) && !birthDate.matches("\\d\\d/\\d\\d/\\d\\d\\d\\d")) {
            return JsonResponse.error();
        }
        List<Client> clients = clientRepository.findByBirthDate(birthDate, new PageRequest(0, 5));
        if(CollectionUtils.isEmpty(clients)) return JsonResponse.error();
        List<ClientInfoDTO> clientInfoDTOS = mapperFacade.mapAsList(clients, ClientInfoDTO.class);
        return JsonResponse.success(clientInfoDTOS);
    }

    // get by id
    @GetMapping("/{clientId}")
    @ApiOperation(value = "Get by id")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public JsonResponse<ClientViewDTO> getById(@PathVariable("clientId") Long clientId) {
        Client client = clientRepository.findOne(clientId);
        ClientViewDTO clientViewDTO = mapperFacade.map(client, ClientViewDTO.class);
        return JsonResponse.success(clientViewDTO);
    }

    // update
    @PostMapping("/{clientId}")
    @ApiOperation(value = "Update client")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public JsonResponse update(@PathVariable("clientId") Long clientId,
                                              @RequestBody @Valid ClientUpdateDTO clientUpdateDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JsonResponse.formError(bindingResult.getAllErrors());
        }
        Client client = clientService.update(clientId, clientUpdateDTO);
        return JsonResponse.success(client.getId());
    }

    // create
    @PostMapping
    @ApiOperation(value = "Create client")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public JsonResponse create(@RequestBody @Valid ClientSaveDTO clientSaveDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JsonResponse.formError(bindingResult.getAllErrors());
        }
        Client client = clientService.create(clientSaveDTO);
        return JsonResponse.success(client.getId());
    }
}
