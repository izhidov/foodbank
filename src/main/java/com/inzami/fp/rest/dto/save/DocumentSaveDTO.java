package com.inzami.fp.rest.dto.save;

import com.inzami.fp.enums.DocumentType;
import com.inzami.fp.rest.dto.update.MemberUpdateDTO;
import com.inzami.fp.rest.dto.view.ClientViewDTO;
import com.inzami.fp.rest.dto.view.MemberViewDTO;
import lombok.Data;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Data
public class DocumentSaveDTO {

    private DocumentType type = DocumentType.VOUCHER;
    private String number;
    private ClientViewDTO client;
    private List<MemberUpdateDTO> members = new ArrayList<>();
    private String createdAt;
}
