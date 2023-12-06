/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2021.  Lorem XiaoMiSum (mi_xiao@qq.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * 'Software'), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED 'AS IS', WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.xiaomisum.mstar.controller.login.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthMenuRespVO {

    /**
     * 菜单id
     */
    private Long id;

    /**
     * 父id
     */
    private Long parentId;

    /**
     * 名称
     */
    private String name;

    /**
     * 路径
     */
    private String path;

    /**
     * 组件
     */
    private String component;

    /**
     * 组件
     */
    private String componentName;

    /**
     * 图标
     */
    private String icon;

    /**
     * 是否可见
     */
    private Boolean visible;

    /**
     * 总是显示
     */
    private Boolean alwaysShow;

    /**
     * 是否缓存
     */
    private Boolean keepAlive;
    /**
     * 是否后台页面
     */
    private Integer pageType;

    /**
     * 子路由
     */
    private List<AuthMenuRespVO> children;
}