package com.chen.imagemanage.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class RightVO {
    /**
     * 该数据集属于团队还是个人
     * value：团队/个人
     */
    private String ownerKind;

    /**
     * 该用户是否是数据集拥有者
     * 个人：创建数据集者 团队：团队成员
     * value: 拥有者/参观者
     */
    private String role;

    /**
     * 是否拥有删除数据的权力
     */
    private Integer ableDelete;

    /**
     * 是否拥有添加数据的权力
     */
    private Integer ableAdd;

    /**
     * 是否拥有新建数据集的权力
     */
    private Integer ableCreateSet;

    /**
     * 是否拥有删除数据集的权力
     */
    private Integer ableDeleteSet;

    /**
     * 是否拥有编辑信息的权力（团队：管理成员的权力）
     * 判断是否是 个人：创建者 团队：创建者
     * value：0 没有 1 有
     */
    private Integer ableChange;
}
