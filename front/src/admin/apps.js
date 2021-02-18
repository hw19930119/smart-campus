/*
 * @(#) apps.js
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * Copyright:  Copyright (c) 2017
 * Company:厦门畅享信息技术有限公司
 * @author Enboga
 * 2017-11-17 10:45
 */

let apps = [
    // {
    //     key: 'shareui',
    //     label: '我的审核',
    //     iconClass: 'fa fa-list-alt',
    //     children:[
    //         {
    //             key: 'shareui-text',
    //             iconClass: 'fa si si-app_sjzl',
    //             label: '首页',
    //             tabLabel: '',
    //             url: "audit.html"
    //         },{
    //             key: 'shareui-text2',
    //             iconClass: 'fa si si-com_lb1',
    //             label: '待审列表',
    //             tabLabel: '',
    //             url: "query.html?g_schema_key=VW_SCF_TODO_FA"
    //         },
    //         {
    //             key: 'shareui-text3',
    //             iconClass: 'fa si si-com_lb1',
    //             label: '已审列表',
    //             tabLabel: '',
    //             url: "query.html?g_schema_key=VW_SCF_DONE"
    //         },
    //         {
    //             key: 'shareui-text4',
    //             iconClass: 'fa si si-com_lb1',
    //             label: '全部列表',
    //             tabLabel: '',
    //             url: "query.html?g_schema_key=stat_sum_qh"
    //         }
    //     ]
    // },
    {
        key: 'shareui',
        label: '指标模板管理',
        iconClass: 'fa fa-list-alt',
        children:[
            {
                key: 'pzgl',
                iconClass: 'fa si si-app_pzgl',
                label: '批次管理',
                tabLabel: '',
                children:[
                    {
                        key: 'mbgl',
                        iconClass: 'fa si fa-list-alt',
                        label: '申报模板管理',
                        tabLabel: '',
                        url: "query.html?g_schema_key=T_ZHXY_PC_FA#/"
                    }
                ]
            },
            {
                key: 'xtgl',
                iconClass: 'fa si si-app_xtgl',
                label: '系统管理',
                tabLabel: '',
                children:[
                    {
                        key: 'jsgl',
                        iconClass: 'fa si si-app_gzrygl',
                        label: '角色管理',
                        tabLabel: '',
                        url: "query.html?g_schema_key=T_SC_ROLE_FA#/"
                    },
                    {
                        key: 'shyhgl',
                        iconClass: 'fa si si-app_gzrygl',
                        label: '审核用户管理',
                        tabLabel: '',
                        url: "query.html?g_schema_key=T_SC_AUDIT_USER_FA#/"
                    },
                    {
                        key: 'xxyhgl',
                        iconClass: 'fa si si-app_gzrygl',
                        label: '学校用户管理',
                        tabLabel: '',
                        url: "query.html?g_schema_key=VW_APPLY_USER_FA#/"
                    },
                    {
                        key: 'lcdygl',
                        iconClass: 'fa si si-app_xtgl',
                        label: '流程定义管理',
                        tabLabel: '',
                        url: "query.html?g_schema_key=T_SCF_PROCDEF_FA#/"
                    },
                    {
                        key: 'yfblcdy',
                        iconClass: 'fa si si-app_xtgl',
                        label: '已发布流程定义',
                        tabLabel: '',
                        url: "query.html?g_schema_key=VW_SCF_PROCDEF_FA#/"
                    }
                ]
            }
        ]
    },
    // {
    //     key: 'shareui',
    //     label: '我要申报',
    //     iconClass: 'fa fa-list-alt',
    //     children:[
    //         {
    //             key: 'shareui-text',
    //             iconClass: 'fa si si-app_xqdm',
    //             label: '申报管理',
    //             tabLabel: '',
    //             // url: "query.html?g_schema_key=T_MASK_LOTTERY_JG_HX_FA",
    //             url: ""
    //         }
    //     ]
    // },

];

//获得菜单，这边的例子是简单从APP中配置获取，真实应用可能不一样，可能从后台获取
let getMenusByApps = function (appId) {
    return getMenus(appId, apps);
};

let getMenus = function (appId, list) {
    let menusTemp = [];
    for(let i=0; i<list.length; i++) {
        let item = list[i];
        if(item.key===appId){
            menusTemp = item.menus;
            break;
        }else if(item.children){
            menusTemp = getMenus(appId, item.children);
        }
    }

    return menusTemp;
};

export default {
    apps,
    getMenus: getMenusByApps
};
