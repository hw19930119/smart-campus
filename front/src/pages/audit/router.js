import React from 'react';
import { Router, Switch, Route } from 'dva/router';
import dynamic from 'dva/dynamic';

const App = ({ children }) => <div>{children}</div>;

function RouterConfig({ history, app }) {
    const Home = dynamic({
        app,
        component: () => import('./routes/Home')
    });
    const Audit = dynamic({
        app,
        component: () => import('./routes/Audit/Audit')
    });
    const HomePage = dynamic({
          app,
          component: () => import('./routes/HomePage/HomePage')
      });
    const AuditScore = dynamic({
        app,
        component: () => import('./routes/AuditScore/AuditScore')
    });
    const AuditExpert = dynamic({
        app,
        component: () => import('./routes/AuditExpert/Audit')
    });
    const ExpertComment = dynamic({
        app,
        component: () => import('./routes/ExpertComment/ExpertComment')
    });
    const WordDownLoad = dynamic({
        app,
        component: () => import('./routes/WordDownLoad/WordDownLoad')
    });
    const routes = [
        {
            key: 'index',
            path: '/',
            component: HomePage,
            exact: true
        },
        {
            key: 'audit',
            path: '/audit/:style/:businessKey/:declareId/:schema?',
            component: Audit,
            exact: true
        },
        {
            key: 'auditExpert',
            path: '/auditExpert/:style/:businessKey/:declareId',
            component: AuditExpert,
            exact: true
        },
        {
            key: 'auditScore',
            path: '/auditScore/:businessKey/:declareId/:type?',
            component: AuditScore,
            exact: true
        },
        {
            key: 'expertComment',
            path: '/expertComment/:businessKey/:declareId/:type?',
            component: ExpertComment,
            exact: true
        },
        {
            key: 'wordDownLoad',
            path: '/wordDownLoad',
            component: WordDownLoad,
            exact: true
        },
    ];

    return (
        <Router history={history}>
            <App>
                <Switch>{routes.map(route => <Route {...route} />)}</Switch>
            </App>
        </Router>
    );
}

export default RouterConfig;
