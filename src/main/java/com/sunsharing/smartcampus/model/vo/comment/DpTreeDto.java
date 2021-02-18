/*
 * @(#) DpTreeDto
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-10-30 09:52:52
 */

package com.sunsharing.smartcampus.model.vo.comment;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Builder
@Accessors(chain = true)
public class DpTreeDto {

    private List<DpTreeDto> children;

    private String key;

    private String title;

    private List<String> expertList;

    private List<String> expertNames;

    private List<String> context;


    public synchronized void addDpTreeDto(DpTreeDto treeDto) {
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(treeDto);

    }

    public synchronized void addExpert(String expertId) {
        if (expertList == null) {
            expertList = new ArrayList<>();
        }

        if (!expertList.contains(expertId)) {
            expertList.add(expertId);
        }

    }


    public synchronized boolean addExpertName(String userName) {
        if (expertNames == null) {
            expertNames = new ArrayList<>();
        }
        if (!expertNames.contains(userName)) {
            expertNames.add(userName);
            return true;
        }
        return false;
    }

    public synchronized void addContext(String text) {
        if (context == null) {
            context = new ArrayList<>();
        }
        context.add(text);
    }
}
