/*
 * @(#) query_list.js
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author lihui
 * <br> 2020-08-12 16:06:44
 */
import React from 'react';
import {QueryList} from '@share/scurd/block/query_list/query_list';
import {UrlUtils} from '@share/scurd/block/Utils';
let CONTEXT = UrlUtils.getContextPath();
import "../../../VW_APPLYUSER_AUDIT_FA/block/query_list/index.scss";
import "./index.scss";
class QueryListAudit extends QueryList {
    constructor(props) {
        super(props);
    }
    stateFun(item,data){
        return `<div>
                    <span class=${data.__STATE == "A03" ? "approve" : data.__STATE == "A04" ? "approve-no" : ""}>${item}</span>
                </div>`
    }
    MATERIALFUN(item){
        if(!item){
            return `<div></div>`;
        }
        if(item.split(",")[0] == undefined){
            return `<div></div>`
        }
        let svg = item.split(",")[0].split(".")[1] || "";
        let name = item.split(",")[1].split("$$")[0] || "";
        let size = Number(item.split("$$")[1] || 0);
        let src = CONTEXT+"/resource"+item.split(",")[0];
        svg = svg.toLowerCase();
        let MATERIAL = `<div class="item-file-box">
                            <div class="svg-type">
                            ${  svg &&
                                svg == "pdf" ? `<svg t="1597112921877" className="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="8515" width="36" height="36"><path d="M581.290667 0a42.666667 42.666667 0 0 1 28.117333 10.538667l314.709333 275.370666a42.666667 42.666667 0 0 1 14.549334 32.128V469.333333a85.333333 85.333333 0 0 1 85.333333 85.333334v213.333333a85.333333 85.333333 0 0 1-85.333333 85.333333v42.666667a128 128 0 0 1-120.490667 127.786667L810.666667 1024H213.333333a128 128 0 0 1-127.786666-120.490667L85.333333 896v-42.666667a85.333333 85.333333 0 0 1-85.333333-85.333333v-213.333333a85.333333 85.333333 0 0 1 85.333333-85.333334V128A128 128 0 0 1 205.824 0.213333L213.333333 0h367.957334zM853.333333 853.333333H170.666667v42.666667a42.666667 42.666667 0 0 0 37.674666 42.368L213.333333 938.666667h597.333334a42.666667 42.666667 0 0 0 42.368-37.674667L853.333333 896v-42.666667z m-338.432-316.842666h-100.266666V810.666667h100.266666c44.501333 0 77.952-12.288 100.992-36.864 21.845333-23.424 33.024-56.832 33.024-100.224 0-43.776-11.136-77.184-33.024-100.224-23.04-24.576-56.490667-36.864-100.992-36.864z m338.432 0h-162.218666V810.666667h44.928v-121.344H853.333333v-38.4h-117.248v-76.032H853.333333v-38.4z m-576.512 0H170.666667V810.666667h37.418666v-105.216H276.053333c66.816 0 100.224-28.416 100.224-84.864 0-56.064-33.450667-84.096-99.498666-84.096z m229.589334 38.4c34.218667 0 59.136 7.68 74.88 23.424 15.36 15.36 23.04 40.704 23.04 75.264 0 33.792-7.68 58.752-23.04 74.88-15.744 15.744-40.704 23.808-74.88 23.808h-46.848v-197.376h46.848z m-233.045334 0c19.541333 0 34.133333 3.456 43.349334 10.752 9.216 6.912 14.208 18.432 14.208 34.944 0 16.512-4.565333 28.416-13.824 35.712-9.216 6.912-23.808 10.752-43.776 10.752h-65.28v-92.16h65.28zM512 85.333333H213.333333a42.666667 42.666667 0 0 0-42.368 37.674667L170.666667 128v341.333333h682.666666V384h-213.333333a128 128 0 0 1-127.146667-113.066667l-0.64-7.424L512 256V85.333333z m85.333333 18.474667V256a42.666667 42.666667 0 0 0 37.674667 42.368L640 298.666667h167.338667L597.333333 103.808z" fill="#FF6666" p-id="8516"></path></svg>`
                                : svg == "doc" ? `<svg t="1597058710776" className="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="4673" width="36" height="36"><path d="M192 384h640a42.666667 42.666667 0 0 1 42.666667 42.666667v362.666666a42.666667 42.666667 0 0 1-42.666667 42.666667H192v106.666667a21.333333 21.333333 0 0 0 21.333333 21.333333h725.333334a21.333333 21.333333 0 0 0 21.333333-21.333333V308.821333L949.909333 298.666667h-126.528A98.048 98.048 0 0 1 725.333333 200.618667V72.661333L716.714667 64H213.333333a21.333333 21.333333 0 0 0-21.333333 21.333333v298.666667zM128 832H42.666667a42.666667 42.666667 0 0 1-42.666667-42.666667V426.666667a42.666667 42.666667 0 0 1 42.666667-42.666667h85.333333V85.333333a85.333333 85.333333 0 0 1 85.333333-85.333333h530.026667L1024 282.453333V938.666667a85.333333 85.333333 0 0 1-85.333333 85.333333H213.333333a85.333333 85.333333 0 0 1-85.333333-85.333333v-106.666667zM65.92 472.490667V746.666667h91.349333c40.597333 0 71.04-12.288 92.053334-36.864 19.946667-23.424 30.101333-56.832 30.101333-100.224 0-43.776-10.154667-77.184-30.101333-100.224-20.992-24.576-51.456-36.864-92.053334-36.864H65.92z m40.96 38.4H149.546667c31.146667 0 53.909333 7.68 68.266666 23.424 13.994667 15.36 20.992 40.704 20.992 75.264 0 33.792-6.997333 58.752-21.013333 74.88-14.336 15.744-37.098667 23.808-68.245333 23.808H106.88v-197.376z m322.005333-43.776c-38.506667 0-68.608 13.44-90.304 41.088-21.013333 26.112-31.146667 59.904-31.146666 101.76 0 41.472 10.133333 75.264 31.146666 101.376 21.696 26.88 51.797333 40.704 90.304 40.704 38.144 0 68.245333-13.44 90.304-40.32 20.992-25.728 31.488-59.52 31.488-101.76 0-42.24-10.496-76.416-31.488-102.144-22.058667-27.264-52.16-40.704-90.304-40.704z m0 39.552c25.898667 0 45.845333 8.832 59.84 27.264 13.653333 18.432 20.650667 43.776 20.650667 76.032s-6.997333 57.216-20.650667 75.264c-13.994667 18.048-33.941333 27.264-59.84 27.264-25.898667 0-46.208-9.6-60.202666-28.416-13.653333-18.432-20.309333-43.008-20.309334-74.112 0-31.488 6.656-56.064 20.309334-74.496 14.336-19.2 34.304-28.8 60.202666-28.8z m268.8-39.552c-39.893333 0-70.357333 14.208-91.349333 43.392-18.56 25.344-27.648 58.752-27.648 99.456 0 41.472 8.746667 74.496 26.602667 99.072 20.288 28.416 51.434667 43.008 93.098666 43.008 26.944 0 50.048-8.448 69.290667-25.344 20.650667-18.048 33.6-43.008 39.210667-75.264h-39.893334c-4.906667 20.736-13.312 36.48-25.216 46.848-11.2 9.216-25.898667 14.208-43.733333 14.208-27.306667 0-47.616-9.6-60.565333-28.032-11.904-17.28-17.856-42.24-17.856-74.496 0-31.488 5.973333-56.064 18.218666-73.728 13.290667-19.968 32.896-29.568 59.136-29.568 17.493333 0 31.850667 3.84 43.050667 12.288 11.562667 8.448 19.264 21.504 23.466667 39.552h39.893333c-3.84-27.648-14.72-49.92-32.896-66.048-18.901333-16.896-43.413333-25.344-72.810667-25.344z" fill="#1296db" p-id="4674"></path></svg>`
                                : svg == "docx" ? `<svg t="1597059028738" className="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="6350" width="36" height="36"><path d="M354.40128 0c-87.04 0-157.44 70.55872-157.44 157.59872v275.68128H78.72c-21.6576 0-39.36256 17.69984-39.36256 39.36256v236.31872c0 21.6576 17.69984 39.35744 39.36256 39.35744h118.24128v118.08256c0 87.04 70.4 157.59872 157.44 157.59872h472.63744c87.04 0 157.59872-70.55872 157.59872-157.59872V315.0336c0-41.74848-38.9888-81.93024-107.52-149.27872l-29.11744-29.12256L818.87744 107.52C751.5392 38.9888 711.39328 0 669.59872 0H354.4064z m0 78.72h287.20128c28.35456 7.0912 27.99616 42.1376 27.99616 76.8v120.16128c0 21.6576 17.69984 39.35744 39.36256 39.35744h118.07744c39.38816 0 78.87872-0.0256 78.87872 39.36256v512c0 43.32032-35.55328 78.87872-78.87872 78.87872H354.4064c-43.32544 0-78.72-35.5584-78.72-78.87872v-118.08256h393.91744c21.66272 0 39.36256-17.69472 39.36256-39.35744V472.64256c0-21.66272-17.69984-39.36256-39.36256-39.36256H275.68128V157.59872c0-43.32032 35.39456-78.87872 78.72-78.87872zM308.6336 498.07872c23.04 0 41.28256 8.00256 54.72256 24.00256 14.08 15.36 21.12 37.43744 21.12 66.23744 0 29.44-7.04 51.84-21.12 67.2-13.44 15.36-31.68256 23.04-54.72256 23.04-23.68 0-42.24-7.35744-55.68-22.07744-13.44-15.36-20.15744-38.08256-20.15744-68.16256 0-29.44 6.71744-51.84 20.15744-67.2s32-23.04 55.68-23.04z m186.24 0.96256c17.92 0 33.28 3.2 46.08 9.6l-9.6 19.2c-12.16-6.4-24.32-9.6-36.48-9.6-16.64 0-30.39744 6.4-41.27744 19.2-10.24 12.16-15.36 29.44-15.36 51.84 0 23.04 4.79744 40.63744 14.39744 52.79744 9.6 11.52 23.68 17.28 42.24 17.28 10.24 0 23.36256-2.23744 39.36256-6.71744v19.2c-11.52 4.48-25.92256 6.71744-43.20256 6.71744-23.68 0-42.24-7.35744-55.68-22.07744-13.44-15.36-20.15744-38.08256-20.15744-68.16256 0-28.16 7.04-49.92 21.12-65.28 14.72-16 34.23744-23.99744 58.55744-23.99744z m-421.43744 1.92h48.95744c24.96 0 44.48256 7.68 58.56256 23.04 14.72 14.72 22.07744 35.84 22.07744 63.36 0 28.8-7.68 50.87744-23.04 66.23744-14.72 15.36-35.51744 23.04-62.39744 23.04h-44.16V500.96128z m478.08 0h23.99744l39.36256 67.2 40.32-67.2h23.04l-50.88256 83.51744 54.72256 92.16h-24.96l-43.20256-75.83744-43.19744 75.83744h-23.04l54.71744-92.16-50.87744-83.51744z m-242.88256 17.28c-17.28 0-30.39744 6.07744-39.35744 18.23744-8.96 11.52-13.44 28.8-13.44 51.84 0 23.68 4.48 41.6 13.44 53.76 8.96 11.52 22.07744 17.28 39.35744 17.28s30.40256-5.76 39.36256-17.28c8.96-12.16 13.44-30.08 13.44-53.76 0-23.04-4.48-40.32-13.44-51.84-8.96-12.16-22.08256-18.23744-39.36256-18.23744z m-213.12 1.92v137.27744h19.2c21.76 0 37.76-5.76 48-17.28 10.88-11.52 16.32256-28.8 16.32256-51.84s-5.12-39.99744-15.36-50.87744c-9.6-11.52-24.32-17.28-44.16-17.28h-24.00256z" p-id="6351" fill="#1296db"></path></svg>`
                                : svg == "jpg" ? `<img src="${src}"/>` : `<span></span>`
            }
                            </div>
                            ${
            svg == "jpg" ? `<span></span>` :
                                `<div>
                                    <div class="hidden-name">${name || ""}</div>
                                    <div>${Math.floor((size || 0)/1024)}KB</div>
                                </div>`
            }
                            
                        </div>`;
        return MATERIAL;
    }
    downloadFujian(id,item){
        if(!item.MATERIAL && !item.MATERIAL.split(",")[0]){
            alert("文件下载出错");
            return;
        }
        let path = CONTEXT+"/resource"+item.MATERIAL.split(",")[0];
        window.location.href = path;
    }
}
export default QueryListAudit;
