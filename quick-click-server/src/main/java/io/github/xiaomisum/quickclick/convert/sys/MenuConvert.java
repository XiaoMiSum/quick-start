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

package io.github.xiaomisum.quickclick.convert.sys;

import io.github.xiaomisum.quickclick.controller.sys.permission.menu.vo.MenuAddReqVO;
import io.github.xiaomisum.quickclick.controller.sys.permission.menu.vo.MenuRespVO;
import io.github.xiaomisum.quickclick.controller.sys.permission.menu.vo.MenuSimpleRespVO;
import io.github.xiaomisum.quickclick.controller.sys.permission.menu.vo.MenuUpdateReqVO;
import io.github.xiaomisum.quickclick.dal.dataobject.sys.Menu;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MenuConvert {
    MenuConvert INSTANCE = Mappers.getMapper(MenuConvert.class);

    List<MenuRespVO> convert(List<Menu> list);

    List<MenuSimpleRespVO> convert0(List<Menu> list);

    MenuRespVO convert(Menu menu);

    Menu convert(MenuAddReqVO addReq);

    Menu convert(MenuUpdateReqVO updateReq);
}
