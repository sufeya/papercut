package com.lwc.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 修改密码参数
 * @author liuweichun
 * @date 2022/8/14 23:14
 * @company Hangzhou Yunphant internet technology co.ltd
 */
@Data
public class UpdatePassWordParam {
    @NotEmpty
    @ApiModelProperty(value = "用户名", required = true)
    private String username;
    @NotEmpty
    @ApiModelProperty(value = "旧密码", required = true)
    private String oldPassword;
    @NotEmpty
    @ApiModelProperty(value = "新密码", required = true)
    private String newPassword;

}
