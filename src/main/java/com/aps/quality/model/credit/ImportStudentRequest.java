package com.aps.quality.model.credit;

import lombok.Data;

import java.io.Serializable;

@Data
public class ImportStudentRequest implements Serializable {
    private String userCode;
    private String userName;
    private String userGender;
    private String userPhone;
    private String teacherName;
    private String teacherPhone;
}
