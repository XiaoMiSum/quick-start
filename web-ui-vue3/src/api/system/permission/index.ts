import request from '@/config/axios'

// 查询角色拥有的菜单数组
export const listRoleMenus = (roleId: any) => {
  return request.get({
    url: '/role/' + roleId + '/menu'
  })
}

// 赋予角色菜单
export const assignRoleMenu = (roleId: any, data: any) => {
  return request.post({
    url: '/role/' + roleId + '/menu',
    data
  })
}
