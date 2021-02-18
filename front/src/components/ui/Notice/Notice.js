import React from 'react';
import BaseComponent from '../../BaseComponent';
import {
    Panel,
    Checkbox,
    Button
} from '@share/shareui';
import './Notice.scss';
import {setData,getDataFromStore} from "../../../utils/StoreUtil";


class Notice extends BaseComponent{
    constructor (){
        super();
        this.state = {
            isRead: false, /* 是否阅读须知*/
        };
        this.handleRead = this.handleRead.bind(this);
    }
    handleRead(){
        const { isRead } = this.state;
        this.setState({ isRead: !isRead });
    }
    toNext =()=>{
        const { isRead } = this.state;
        if (!isRead){
            window.info("请先勾选“我已阅读并确认上述”再继续操作");
            return
        }
        let stepSelected = getDataFromStore('stepSelected');
        stepSelected = stepSelected ? stepSelected.concat(['1']) : ['1'];
        setData('stepSelected',stepSelected)
        this.props.onPath('/apply/1')
    }
    render(){
        const { isRead } = this.state;
        let { message }= this.props;

        return (
            <div className="notice-con">
                <Panel>
                    <Panel.Head title="厦门市智慧校园创建申报须知" />
                    <Panel.Body>
                        <div className="notice">
                            <div className="noticeContent">
                                <h3>关于厦门市智慧校园创建工作的通知</h3>
                                <p>
                                    <h4>各区教育局、市直属学校：</h4>
                                为全面贯彻落实《厦门教育现代化2035》和《厦门教育信息化2.0行动计划》等文件要求，统筹推进我市智慧校园建设，促进学习方式、教学方式、管理方式、评价方式的变革与创新，我局决定经过三年的努力，培育和树立一批教育信息化创新应用的示范典型，现将有关事项通知如下：<br/>
                                    <h4>一、工作目标</h4>
                                在全市范围内，按照科学的发展理念，以新一代信息技术和智慧应用为支撑，在泛在信息全面感知和互联的基础上，全面整合校内外资源，实现人、物、校区功能系统之间无缝连接与协同联动的智能自感知、自适应、自优化，从而智能识别师生群体的学习、工作情景和个体的特征，将学校物理空间和数字空间有机衔接起来，为师生建立智能开放的教育教学环境，改变师生与学校资源、环境的交互方式，提高教育教学质量和管理水平，促进师生全面发展，形成可总结、可推广的示范典型，计划到2022年建成80所中小学智慧校园示范校。<br/>

                                    <h4>二、建设原则</h4>
                                （一）总体规划、分步实施。从全市教育信息化发展的实际问题入手，注重智慧校园顶层设计和市区统筹规划，在规划指引下，每年评估确认一批智慧校园学校，突出重点、以点带面、分步实施、逐步深入，不断增强实施应用效果，统筹推进学校信息化协调发展。<br/>
                                （二）统一标准、分类指导。规范智慧校园建设，建设过程注重统筹、论证与监督标准先行，突出市区校数据互联共享和数据治理。原则上，市区校产生的各类教育数据应实现实时共享，由市区统筹建设的区域性教育信息系统和基础数据平台，学校不得重复建设。学校应以需求强烈的智慧教学管理应用为突破，由点及面有序推动智慧校园建设。<br/>
                                （三）模式探索、融合创新。充分利用智能化教育环境，突出培养教师的教育技术能力、信息环境下教学创新的能力，学生的自主学习能力、协作学习能力、探究学习能力和信息技术素养，促进信息技术与教育教学核心业务的深度融合，实现教与学方式和教育模式的变革与创新。<br/>

                                    <h4>三、评价内容和要求</h4>
                                （一）评价内容。评价指标共分基础环境建设、应用服务、师生发展、保障措施、特色创新、示范引领六大类69小项，评价总分值200分，三星达标分值140分，四星达标分值160分，五星达标分值180分（详见附件1）。<br/>
                                （二）申报要求。原则上，自评分值达到140分以上的学校方可申报。各校要成立以校长为组长的智慧校园创建领导小组、工作小组，加强对创建工作的领导。市区教育局参与创建工作的组织、实施和指导，保障学校的创建工作有序推进；市区两级教育部门应严格把关，按照评价指标择优推荐，区属学校由区教育局推荐，市直属学校直接申报。<br/>
                                </p>

                            </div>
                            <Checkbox
                                name="checkbox" block
                                checked={isRead}
                                onChange={this.handleRead}
                            >我已阅读并确认上述</Checkbox>
                            <div className="btnBox">
                                <Button type="button" bsStyle="default" onClick={() =>this.props.onPath('/')}>返回</Button>
                                <Button type="button" bsStyle="primary" disabled={message != ''} onClick={() => this.toNext()}>创建申报</Button>
                            </div>
                        </div>
                    </Panel.Body>
                </Panel>
            </div>
        );
    }
}

export default Notice;
