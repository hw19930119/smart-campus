import 'bootstrap/dist/css/bootstrap.min.css';
import 'font-awesome/css/font-awesome.min.css';
import '@share/shareui-html';
import '@share/shareui-font/dist/style.css';
import '@share/shareui-portal-html';
import React, { Component } from 'react';
import { render } from 'react-dom';
import {
    HashRouter as Router,
    Route,
    withRouter
} from 'react-router-dom';
import Bundle from '@share/bundle';
import loadPortalPage from 'bundle-loader?lazy&name=js/queryPage!./Portal';

let IndexPage = () => (
    <Bundle load={loadPortalPage}>
        {IndexPage => <IndexPage/>}
    </Bundle>
);


render(
    <Router>
        <div className="ui-full-page">
            <Route exact path="/" component={withRouter(IndexPage)}/>
        </div>
    </Router>,
    document.getElementById('root')
);