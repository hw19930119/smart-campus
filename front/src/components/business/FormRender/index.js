import React from 'react';
import FormRender from '@share/form-render/lib/shareui';
import fileUpload from './components/fileUpload';
import imageUploader from './components/imageUploader';
import dataDisplay from './components/dataDisplay';
import xzqh from './components/xzqh';

export default function(props) {
    return (
        <FormRender
            widgets={{ imageUploader, fileUpload, xzqh, dataDisplay }}
            mapping={{ xzqh: 'xzqh', dataDisplay: 'dataDisplay' }}
            {...props}
        />
    );
}
