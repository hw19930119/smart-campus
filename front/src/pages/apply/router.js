import React from 'react';
import { Router, Switch, Route } from 'dva/router';
import dynamic from 'dva/dynamic';

const App = ({ children }) => <div>{children}</div>;

function RouterConfig({ history, app }) {
    const Home = dynamic({
        app,
        component: () => import('./routes/Home')
    });
    const Apply = dynamic({
        app,
        component: () => import('./routes/Apply/Apply')
    });
    const SchApprove = dynamic({
                              app,
                              component: () => import('./routes/SchApprove/SchApprove')
                          });
    const OverHome = dynamic({
                                 app,
                                 component: () => import('./routes/OverHome/OverHome')
                             });
    const routes = [
        {
            key: 'index',
            path: '/',
            component: Home,
            exact: true
        },
        {
            key: 'overHome',
            path: '/overHome',
            component: OverHome,
            exact: true
        },
        {
            key: 'apply',
            path: '/apply/:step',
            component: Apply,
            exact: true
        },{
            key: 'SchApprove',
            path: '/schApprove/:status',
            component: SchApprove,
            exact: true
        },
        // {
        //     key: 'tet',
        //     path: '/test',
        //     component: FormTest,
        //     exact: true
        // }
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
