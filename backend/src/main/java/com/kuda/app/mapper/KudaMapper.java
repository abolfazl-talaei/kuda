package com.kuda.app.mapper;

import org.springframework.http.HttpStatus;

import com.kuda.app.dto.response.KudaInfo;
import com.kuda.app.exception.Errors;
import com.kuda.app.exception.KudaException;
import com.kuda.app.exception.Resources;
import com.kuda.app.model.Kuda;

public class KudaMapper {

    private KudaMapper() throws KudaException {
        throw new KudaException(Errors.INTERNAL_ERROR.getName(), HttpStatus.INTERNAL_SERVER_ERROR,
                Resources.UTIL.getName(), "103");
    }

    public static KudaInfo map(Kuda kuda) {

        KudaInfo kudaInfo = new KudaInfo();

        kudaInfo.setCreationTime(kuda.getCreationTime());
        kudaInfo.setDescription(kuda.getDescription());
        kudaInfo.setDuration(kuda.getDuration());
        if (kuda.getFromUser() != null) {
            kudaInfo.setFromUser(kuda.getFromUser().getName());
            kudaInfo.setFromUsername(kuda.getFromUser().getUsername());
        }
        kudaInfo.setId(kuda.getId());
        kudaInfo.setKudaType(kuda.getKudaType());
        kudaInfo.setRequestId(kuda.getRequestId());
        kudaInfo.setReadStatus(kuda.getReadStatus());

        return kudaInfo;
    }
}
