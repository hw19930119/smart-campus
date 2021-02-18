import React from 'react';
import {Modal,Button} from '@share/shareui';
import './ModalBox.scss';
import {throttle} from "../../../utils/util";

class ModalBox extends React.Component{
    constructor(props){
        super(props)
        this.handleCommit = throttle(this.handleCommit)
    }
    handleCommit = ()=>{
        let {handleCommit} = this.props;
        handleCommit && handleCommit()
    }
    render(){
        let {handleModal,handleCommit,title='自评项目',className='',size} = this.props;
        return (
            <div>
                    <Modal className={`${size=='sm' || size=='large'?'modal-box-modal':'evaluation-modal'} ${className}`} show={true}
                           onHide={() => handleModal(false)} bsSize={size=='sm'?undefined:size=='large'?size:'lg'}>
                        <Modal.Header closeButton>
                            <Modal.Title>{title}</Modal.Title>
                        </Modal.Header>
                        <Modal.Body>
                            {
                                this.props.children
                            }
                        </Modal.Body>
                        <Modal.Footer>
                            {
                                handleCommit && <Button onClick={this.handleCommit} bsStyle="primary">提交</Button>
                            }

                            <Button onClick={() => handleModal(false)}>关闭</Button>
                        </Modal.Footer>
                    </Modal>
            </div>
        )

    }
}
export default ModalBox;