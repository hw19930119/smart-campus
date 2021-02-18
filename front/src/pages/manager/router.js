import React from 'react';
import { Router, Switch, Route } from 'dva/router';
import dynamic from 'dva/dynamic';

const App = ({ children }) => <div>{children}</div>;

function RouterConfig({ history, app }) {
    const Home = dynamic({
        app,
        component: () => import('./routes/Home')
    });
    const IndexAllocation = dynamic({
        app,
        component: () => import('./routes/IndexAllocation/IndexAllocation')
    });

    const routes = [
        {
            key: 'index',
            path: '/',
            component: IndexAllocation,
            exact: true
        },{
            key: 'indexAllocation',
            path: '/indexAllocation',
            component: IndexAllocation,
            exact: true
        }
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
