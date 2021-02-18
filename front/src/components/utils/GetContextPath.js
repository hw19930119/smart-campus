import { GlobalOptions }  from '@share/network';

export const getContextPath = () => {
    window.CONTEXT_PATH = window.location.href.replace(/query\.html.*/, '');
    window.getContextPath = () => {
        return window.CONTEXT_PATH;
    };
    GlobalOptions.setContextPath(window.CONTEXT_PATH);
};

