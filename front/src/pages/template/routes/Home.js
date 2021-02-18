import React from 'react';
import { Table } from 'antd';

const data = {
    columns: [
        {
            title: '申报数据ID',
            dataIndex: 'decDataID'
        },
        {
            title: '政策',
            dataIndex: 'policyId'
        },
        {
            title: '申报用户',
            dataIndex: 'decUserId'
        },
        {
            title: '申报时间',
            dataIndex: 'decTime'
        }
    ],
    data: [
        {
            decDataID: '0031d100e97652428724ab18b3af4ba3',
            policyId: 'ddabeb96383e4ea1891fd0ee712fdee3',
            decUserId: 'sunsharing',
            decTime: '2020-06-11 14:46:02'
        },
        {
            decDataID: '01daa60a588528bf52147662c3124788',
            policyId: '656c3dd0e5a84d95b7d4e334eb4dea40',
            decUserId: 'sunsharing',
            decTime: '2020-06-10 14:16:10'
        },
        {
            decDataID: '045ad277c862b3a8c0a4d6a5ca34e8a6',
            policyId: 'ddabeb96383e4ea1891fd0ee712fdee3',
            decUserId: 'sunsharing',
            decTime: '2020-06-11 14:54:02'
        },
        {
            decDataID: '0841e03685d91ddba93e5d1ac2b20a99',
            policyId: 'bdeed094b9164da88af9fffc7426bedc',
            decUserId: 'sunsharing',
            decTime: '2020-06-08 15:21:02'
        },
        {
            decDataID: '0a7a0450250943de5cc5f29434b8e9ac',
            policyId: 'ddabeb96383e4ea1891fd0ee712fdee3',
            decUserId: 'sunsharing',
            decTime: '2020-06-11 14:56:49'
        },
        {
            decDataID: '0e58d37b54832729b089d0e3806e971d',
            policyId: 'ddabeb96383e4ea1891fd0ee712fdee3',
            decUserId: 'jny',
            decTime: '2020-06-11 15:01:42'
        },
        {
            decDataID: '0f1e5efeb750141bb72ed4bafe6625e6',
            policyId: 'bdeed094b9164da88af9fffc7426bedc',
            decUserId: 'sunsharing',
            decTime: '2020-06-08 15:20:08'
        },
        {
            decDataID: '1180f271507d881d2b5cafd7fc348224',
            policyId: 'bce9124637b24a6f9cae1e855c6eee82',
            decUserId: 'sunsharing',
            decTime: '2020-06-06 21:49:33'
        },
        {
            decDataID: '11bec357ee87b177c98a82754a6554f4',
            policyId: '298cfd80b38c4ea1929dd36af0b3dc15',
            decUserId: 'sunsharing',
            decTime: '2020-06-05 12:04:54'
        },
        {
            decDataID: '125a71313f51e409908dfd87828bf635',
            policyId: '298cfd80b38c4ea1929dd36af0b3dc15',
            decUserId: 'sunsharing',
            decTime: '2020-06-05 11:51:45'
        },
        {
            decDataID: '13070e22b29abacdb1953773af03d30f',
            policyId: '3550792ea49f436984781eff897bae26',
            decUserId: 'yry',
            decTime: '2020-06-07 13:07:00'
        },
        {
            decDataID: '14079369314e75ed0479d0f426ba7d92',
            policyId: 'ddabeb96383e4ea1891fd0ee712fdee3',
            decUserId: 'sunsharing',
            decTime: '2020-06-16 10:08:51'
        },
        {
            decDataID: '159c540fdac8f5bb0100f9f3e83c6db8',
            policyId: '0f1c81e7363843c4bd129dc52ed0a959',
            decUserId: 'yry',
            decTime: '2020-06-07 12:25:58'
        },
        {
            decDataID: '1e3053fc6f999eacbad28a85ea71d840',
            policyId: 'ddabeb96383e4ea1891fd0ee712fdee3',
            decUserId: 'sunsharing',
            decTime: '2020-06-10 14:15:10'
        },
        {
            decDataID: '22433985e3e60d5a0047708a035be3eb',
            policyId: 'aa7d89f95fdd42989ab23493ee445083',
            decUserId: 'sunsharing',
            decTime: '2020-06-08 10:24:25'
        },
        {
            decDataID: '224ed1b6513c5957d7c23c03e1249bc1',
            policyId: 'bce9124637b24a6f9cae1e855c6eee82',
            decUserId: 'jny',
            decTime: '2020-06-06 15:41:09'
        },
        {
            decDataID: '2295d4ff0c26f19f1e32964f9892aee0',
            policyId: 'fa5bab3cee354a5784769097bf1a409b',
            decUserId: 'sunsharing',
            decTime: '2020-06-15 15:50:00'
        },
        {
            decDataID: '25841398ef670076f338819d3eef18e3',
            policyId: 'ddabeb96383e4ea1891fd0ee712fdee3',
            decUserId: 'sunsharing',
            decTime: '2020-06-18 16:14:54'
        },
        {
            decDataID: '28626a5f4d7f4cdfb10933f0262ae10c',
            policyId: '1470f91ed77a494c92e2d529ef8a0a96',
            decUserId: 'cxy',
            decTime: '2020-06-06 13:22:13'
        },
        {
            decDataID: '2ac2c76c912a05241ad6eb4bdf35827b',
            policyId: 'ddabeb96383e4ea1891fd0ee712fdee3',
            decUserId: 'sunsharing',
            decTime: '2020-06-11 15:28:10'
        },
        {
            decDataID: '2c2835102c3ba815e72df7080b03e577',
            policyId: 'ddabeb96383e4ea1891fd0ee712fdee3',
            decUserId: 'sunsharing',
            decTime: '2020-06-18 16:14:54'
        },
        {
            decDataID: '2e14ee2ae39df2f7b17ea36f345e1add',
            policyId: '1470f91ed77a494c92e2d529ef8a0a96',
            decUserId: 'cxy',
            decTime: '2020-06-05 10:28:18'
        },
        {
            decDataID: '373f09df66cb8f3b5fe0751217ce44b5',
            policyId: 'ddabeb96383e4ea1891fd0ee712fdee3',
            decUserId: 'sunsharing',
            decTime: '2020-06-07 15:09:34'
        },
        {
            decDataID: '41d6d809ed45f365ae5f3e695699c308',
            policyId: '3550792ea49f436984781eff897bae26',
            decUserId: 'yry',
            decTime: '2020-06-07 15:07:13'
        },
        {
            decDataID: '427333418dc4a3a001910c95869884b3',
            policyId: '656c3dd0e5a84d95b7d4e334eb4dea40',
            decUserId: 'sunsharing',
            decTime: '2020-06-05 11:51:14'
        },
        {
            decDataID: '45539393eb99d199aaa4e896c43ccb92',
            policyId: '3fa83951984648c99dbdd63e05e71bd6',
            decUserId: 'sunsharing',
            decTime: '2020-06-05 10:24:26'
        },
        {
            decDataID: '466389e89b7b75bdaab668d1256037e0',
            policyId: 'bce9124637b24a6f9cae1e855c6eee82',
            decUserId: 'sunsharing',
            decTime: '2020-06-06 21:48:21'
        },
        {
            decDataID: '4758e7eb33c859adf80c30f862eeac3a',
            policyId: 'aa7d89f95fdd42989ab23493ee445083',
            decUserId: 'jny',
            decTime: '2020-06-08 10:42:54'
        },
        {
            decDataID: '4a45db498cf52a43404b6ec4f59c4c01',
            policyId: 'aa7d89f95fdd42989ab23493ee445083',
            decUserId: 'jny',
            decTime: '2020-06-08 10:40:35'
        },
        {
            decDataID: '4def6ea059e0078affa8dc6b4525076b',
            policyId: '7fd85394586840e684e57fd517ea55ae',
            decUserId: 'sunsharing',
            decTime: '2020-06-15 16:33:16'
        },
        {
            decDataID: '5584d9a91a8c52bc52ce064908631c01',
            policyId: 'aa7d89f95fdd42989ab23493ee445083',
            decUserId: 'jny',
            decTime: '2020-06-08 10:33:27'
        },
        {
            decDataID: '57634f9433407856f8276bbb607dbf2b',
            policyId: '298cfd80b38c4ea1929dd36af0b3dc15',
            decUserId: 'sunsharing',
            decTime: '2020-06-05 11:07:53'
        },
        {
            decDataID: '5c55bcaf127409ce10e4a993572aed96',
            policyId: '298cfd80b38c4ea1929dd36af0b3dc15',
            decUserId: 'sunsharing',
            decTime: '2020-06-07 15:17:20'
        },
        {
            decDataID: '5ce8dd4313dc7eedaa70527a502e6b62',
            policyId: '3fa83951984648c99dbdd63e05e71bd6',
            decUserId: 'sunsharing',
            decTime: '2020-06-12 18:00:59'
        },
        {
            decDataID: '5d4862fd0f1f461e24d2188d9a064bc0',
            policyId: '3550792ea49f436984781eff897bae26',
            decUserId: 'yry',
            decTime: '2020-06-07 13:25:51'
        },
        {
            decDataID: '61cfd6ea54636e351a2c2b2f1af6277f',
            policyId: 'ddabeb96383e4ea1891fd0ee712fdee3',
            decUserId: 'jny',
            decTime: '2020-06-11 15:01:43'
        },
        {
            decDataID: '663738e37c4d17a221b25fc6a4aeacf4',
            policyId: '656c3dd0e5a84d95b7d4e334eb4dea40',
            decUserId: 'sunsharing',
            decTime: '2020-06-11 15:01:58'
        },
        {
            decDataID: '68b3637f30ddea0b5a724a7bba7cb7fe',
            policyId: 'bce9124637b24a6f9cae1e855c6eee82',
            decUserId: 'sunsharing',
            decTime: '2020-06-06 21:51:41'
        },
        {
            decDataID: '6ae30525ace01b5eaf17ed38e1e53eb3',
            policyId: 'bce9124637b24a6f9cae1e855c6eee82',
            decUserId: 'sunsharing',
            decTime: '2020-06-06 22:13:48'
        },
        {
            decDataID: '749874b4999132457765bcc0e41f2e23',
            policyId: '7fd85394586840e684e57fd517ea55ae',
            decUserId: 'sunsharing',
            decTime: '2020-06-16 10:07:18'
        },
        {
            decDataID: '74e12dc6c95cc74dcf3c48fd047156b1',
            policyId: '7fd85394586840e684e57fd517ea55ae',
            decUserId: 'sunsharing',
            decTime: '2020-06-18 10:58:08'
        },
        {
            decDataID: '75c23f495ca71be36e3be11773c59931',
            policyId: 'ddabeb96383e4ea1891fd0ee712fdee3',
            decUserId: 'sunsharing',
            decTime: '2020-06-08 15:56:34'
        },
        {
            decDataID: '77ec168164437cd042d2c6ed650e71fe',
            policyId: '7fd85394586840e684e57fd517ea55ae',
            decUserId: 'sunsharing',
            decTime: '2020-06-16 10:44:17'
        },
        {
            decDataID: '77f6ffb723efd899f71de86e2c7f2cb8',
            policyId: 'aa7d89f95fdd42989ab23493ee445083',
            decUserId: 'jny',
            decTime: '2020-06-08 10:45:48'
        },
        {
            decDataID: '7890995fd326220e5002f5e7845b04ad',
            policyId: 'bce9124637b24a6f9cae1e855c6eee82',
            decUserId: 'sunsharing',
            decTime: '2020-06-07 10:37:11'
        },
        {
            decDataID: '8c684d6e3a01e08adae9ddb4f00902cf',
            policyId: '1470f91ed77a494c92e2d529ef8a0a96',
            decUserId: 'cxy',
            decTime: '2020-06-06 13:25:37'
        },
        {
            decDataID: '9015ced304eee3428123c20230a0ed58',
            policyId: 'aa7d89f95fdd42989ab23493ee445083',
            decUserId: 'sunsharing',
            decTime: '2020-06-05 14:14:55'
        },
        {
            decDataID: '9b9a0ddbb4e159ab8b3d1d23f5d25d6b',
            policyId: '3fa83951984648c99dbdd63e05e71bd6',
            decUserId: 'sunsharing',
            decTime: '2020-06-07 12:01:42'
        },
        {
            decDataID: '9c9d84f354b4c83fddbb53f135ca04a9',
            policyId: 'aa7d89f95fdd42989ab23493ee445083',
            decUserId: 'sunsharing',
            decTime: '2020-06-08 10:22:25'
        },
        {
            decDataID: 'a0e0674db3cc8214b73b3ce6066aad68',
            policyId: 'aa7d89f95fdd42989ab23493ee445083',
            decUserId: 'sunsharing',
            decTime: '2020-06-17 16:05:48'
        },
        {
            decDataID: 'a33e5cefc764886352aaf9b454c49df8',
            policyId: 'bce9124637b24a6f9cae1e855c6eee82',
            decUserId: 'sunsharing',
            decTime: '2020-06-12 09:44:24'
        },
        {
            decDataID: 'a8ba5937e2723b2aff42e66e3c6f404e',
            policyId: 'aa7d89f95fdd42989ab23493ee445083',
            decUserId: 'jny',
            decTime: '2020-06-08 10:38:23'
        },
        {
            decDataID: 'aa380bd84affc19546fae57dc3ab6bce',
            policyId: 'aa7d89f95fdd42989ab23493ee445083',
            decUserId: 'sunsharing',
            decTime: '2020-06-05 14:15:30'
        },
        {
            decDataID: 'ab7513fee2a3db1467893faf29eb263e',
            policyId: 'ddabeb96383e4ea1891fd0ee712fdee3',
            decUserId: 'sunsharing',
            decTime: '2020-06-11 15:28:10'
        },
        {
            decDataID: 'af207e5272b66863c999e667f2bf283d',
            policyId: 'aa7d89f95fdd42989ab23493ee445083',
            decUserId: 'jny',
            decTime: '2020-06-08 10:35:26'
        },
        {
            decDataID: 'b3eac7b885be12ec76c8dafb06fa214d',
            policyId: '1470f91ed77a494c92e2d529ef8a0a96',
            decUserId: 'cxy',
            decTime: '2020-06-07 14:47:39'
        },
        {
            decDataID: 'b5cd4c767a841652ac54fd9a20029574',
            policyId: 'e83e4503f31d445c9236ec259d9b02d5',
            decUserId: 'cxy',
            decTime: '2020-06-06 13:22:49'
        },
        {
            decDataID: 'b699fbcb100fb3ff28d934058992387f',
            policyId: 'ddabeb96383e4ea1891fd0ee712fdee3',
            decUserId: 'sunsharing',
            decTime: '2020-06-15 15:15:03'
        },
        {
            decDataID: 'b884c4ca9d10f60c0701c5e3a7ee820b',
            policyId: '3550792ea49f436984781eff897bae26',
            decUserId: 'yry',
            decTime: '2020-06-07 13:18:30'
        },
        {
            decDataID: 'b91cd76b5222f2a3354d29066789d03f',
            policyId: 'aa7d89f95fdd42989ab23493ee445083',
            decUserId: 'jny',
            decTime: '2020-06-08 10:27:03'
        },
        {
            decDataID: 'bc63ccfdca99ebcb033d9ec7ddb24efd',
            policyId: '7fd85394586840e684e57fd517ea55ae',
            decUserId: 'sunsharing',
            decTime: '2020-06-17 16:39:17'
        },
        {
            decDataID: 'bcbe9c1e92022b8b04a244dc85ac7000',
            policyId: 'ddabeb96383e4ea1891fd0ee712fdee3',
            decUserId: 'sunsharing',
            decTime: '2020-06-07 15:09:34'
        },
        {
            decDataID: 'bdd2419fbd300353405bbbec05d38f85',
            policyId: '298cfd80b38c4ea1929dd36af0b3dc15',
            decUserId: 'sunsharing',
            decTime: '2020-06-07 15:19:10'
        },
        {
            decDataID: 'bf0a8924a4c19d264874ba3b25c6dcdd',
            policyId: 'bce9124637b24a6f9cae1e855c6eee82',
            decUserId: 'sunsharing',
            decTime: '2020-06-06 22:01:18'
        },
        {
            decDataID: 'bf4b3742b67dd001882f2664a9cd66fc',
            policyId: '0f1c81e7363843c4bd129dc52ed0a959',
            decUserId: 'yry',
            decTime: '2020-06-07 13:13:11'
        },
        {
            decDataID: 'c0b047fee97b14e7bb835da19b075e99',
            policyId: 'e83e4503f31d445c9236ec259d9b02d5',
            decUserId: 'sunsharing',
            decTime: '2020-06-07 15:14:54'
        },
        {
            decDataID: 'c11f5a40182f470d9b1caa9967719cfc',
            policyId: '7fd85394586840e684e57fd517ea55ae',
            decUserId: 'sunsharing',
            decTime: '2020-06-15 15:55:42'
        },
        {
            decDataID: 'c1b24462e42baee605645886e45c2a3d',
            policyId: '7fd85394586840e684e57fd517ea55ae',
            decUserId: 'sunsharing',
            decTime: '2020-06-12 15:57:46'
        },
        {
            decDataID: 'c347c593ad8b94ed6c8ff901da92ef13',
            policyId: 'aa7d89f95fdd42989ab23493ee445083',
            decUserId: 'jny',
            decTime: '2020-06-08 10:51:00'
        },
        {
            decDataID: 'c3623ba3a0b4638fc6194d8f8926a748',
            policyId: 'e83e4503f31d445c9236ec259d9b02d5',
            decUserId: 'sunsharing',
            decTime: '2020-06-07 14:48:27'
        },
        {
            decDataID: 'c5717e023a46ec3d78185636f27fe4c2',
            policyId: '656c3dd0e5a84d95b7d4e334eb4dea40',
            decUserId: 'sunsharing',
            decTime: '2020-06-05 14:22:04'
        },
        {
            decDataID: 'c9135cf675c3154a4324222c797e2cab',
            policyId: 'ddabeb96383e4ea1891fd0ee712fdee3',
            decUserId: 'jny',
            decTime: '2020-06-11 15:01:12'
        },
        {
            decDataID: 'c9ecb3bacb4a300d7ef1f17f39f57146',
            policyId: 'aa7d89f95fdd42989ab23493ee445083',
            decUserId: 'jny',
            decTime: '2020-06-08 10:30:37'
        },
        {
            decDataID: 'cf8f09ab2ba353aa95defb26910d6975',
            policyId: 'fa5bab3cee354a5784769097bf1a409b',
            decUserId: 'sunsharing',
            decTime: '2020-06-15 15:58:32'
        },
        {
            decDataID: 'd3e6d1320505cf4cf865a2eadbdb8d8c',
            policyId: '3550792ea49f436984781eff897bae26',
            decUserId: 'yry',
            decTime: '2020-06-07 12:59:53'
        },
        {
            decDataID: 'd5c744b438cafa259725cbd2f0f8cab2',
            policyId: 'aa7d89f95fdd42989ab23493ee445083',
            decUserId: 'jny',
            decTime: '2020-06-08 10:41:34'
        },
        {
            decDataID: 'd65df65d5aad96dc359dce0d7da8fbdb',
            policyId: '1470f91ed77a494c92e2d529ef8a0a96',
            decUserId: 'cxy',
            decTime: '2020-06-08 15:48:39'
        },
        {
            decDataID: 'd81be9d9c82886ce10436796c46dd183',
            policyId: '3550792ea49f436984781eff897bae26',
            decUserId: 'yry',
            decTime: '2020-06-07 15:13:07'
        },
        {
            decDataID: 'd86b54b4d07707bbb1c29965b021c76a',
            policyId: '298cfd80b38c4ea1929dd36af0b3dc15',
            decUserId: 'sunsharing',
            decTime: '2020-06-07 15:19:32'
        },
        {
            decDataID: 'db0e589ab0b4f270102696e3c9e2baf8',
            policyId: 'ddabeb96383e4ea1891fd0ee712fdee3',
            decUserId: 'sunsharing',
            decTime: '2020-06-07 15:09:35'
        },
        {
            decDataID: 'dc884ee165117915b29ce92d9a95ae6a',
            policyId: 'ddabeb96383e4ea1891fd0ee712fdee3',
            decUserId: 'sunsharing',
            decTime: '2020-06-11 14:47:49'
        },
        {
            decDataID: 'e1cc147280718c367021e7450124d508',
            policyId: 'ddabeb96383e4ea1891fd0ee712fdee3',
            decUserId: 'sunsharing',
            decTime: '2020-06-07 15:13:07'
        },
        {
            decDataID: 'e2a8b381070520628c3a501636825bfe',
            policyId: 'ddabeb96383e4ea1891fd0ee712fdee3',
            decUserId: 'sunsharing',
            decTime: '2020-06-11 15:16:27'
        },
        {
            decDataID: 'e3256090d89beb24ed857bb6ce9e0048',
            policyId: 'fa5bab3cee354a5784769097bf1a409b',
            decUserId: 'sunsharing',
            decTime: '2020-06-16 10:08:51'
        },
        {
            decDataID: 'e5338fcda093a86e101cb363788eec1e',
            policyId: 'aa7d89f95fdd42989ab23493ee445083',
            decUserId: 'jny',
            decTime: '2020-06-08 10:48:17'
        },
        {
            decDataID: 'eb3f05947d292b2ca4edb496be4a218c',
            policyId: 'bce9124637b24a6f9cae1e855c6eee82',
            decUserId: 'sunsharing',
            decTime: '2020-06-05 10:29:09'
        },
        {
            decDataID: 'ec5731c0cdc6be84be9129316d8cbe5a',
            policyId: '298cfd80b38c4ea1929dd36af0b3dc15',
            decUserId: 'jny',
            decTime: '2020-06-07 13:39:04'
        },
        {
            decDataID: 'f36ff915440236258aaf73994644ab10',
            policyId: 'fa5bab3cee354a5784769097bf1a409b',
            decUserId: 'sunsharing',
            decTime: '2020-06-15 15:46:05'
        },
        {
            decDataID: 'f522d4338a455267b533236df7992ae2',
            policyId: '1fd58efb4d304699a7123f94d00c09bd',
            decUserId: 'sunsharing',
            decTime: '2020-06-12 17:20:01'
        },
        {
            decDataID: 'f5cf5d5201617a4174bbf9648cf980b9',
            policyId: 'aa7d89f95fdd42989ab23493ee445083',
            decUserId: 'jny',
            decTime: '2020-06-08 10:48:26'
        },
        {
            decDataID: 'f6957b1412e8153364875e67b6952e26',
            policyId: 'bce9124637b24a6f9cae1e855c6eee82',
            decUserId: 'sunsharing',
            decTime: '2020-06-06 21:45:25'
        },
        {
            decDataID: 'f6be7e1caba9d8f627146675bc010e15',
            policyId: 'e83e4503f31d445c9236ec259d9b02d5',
            decUserId: 'sunsharing',
            decTime: '2020-06-10 14:16:50'
        },
        {
            decDataID: 'f8abe9bd3493ec2f24118f3cdaa5e959',
            policyId: 'aa7d89f95fdd42989ab23493ee445083',
            decUserId: 'jny',
            decTime: '2020-06-08 10:50:17'
        },
        {
            decDataID: 'fd086f3a4f56e5e9089353c709f1a997',
            policyId: 'ddabeb96383e4ea1891fd0ee712fdee3',
            decUserId: 'sunsharing',
            decTime: '2020-06-18 16:22:03'
        },
        {
            decDataID: 'fed432eb03a7c8401858408d04dd671a',
            policyId: '7fd85394586840e684e57fd517ea55ae',
            decUserId: 'sunsharing',
            decTime: '2020-06-18 16:14:54'
        }
    ]
};

export default class App extends React.Component {
    state = {
        selectedRowKeys: [], // Check here to configure the default column
        loading: false,
    };


    render() {
        const { columns, data: dataSource } = data;

        return (
            <Table columns={columns} dataSource={dataSource} />
        );
    }
}
