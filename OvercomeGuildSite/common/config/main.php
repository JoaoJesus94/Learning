<?php
return [
    'timezone' => 'Europe/London',
    'aliases' => [
        '@bower' => '@vendor/bower-asset',
        '@npm'   => '@vendor/npm-asset',
    ],
    'modules' => [
        'gridview' => [
            'class' => '\kartik\grid\Module',
        ],
    ],
    'vendorPath' => dirname(dirname(__DIR__)) . '/vendor',
    'components' => [
        'imageresize' => [
            'class' => 'noam148\imageresize\ImageResize',
            //path relative web folder. In case of multiple environments (frontend, backend) add more paths
            'cachePath' =>  ['assets/images', '../../frontend/web/img'],
            //use filename (seo friendly) for resized images else use a hash
            'useFilename' => true,
            //show full url (for example in case of a API)
            'absoluteUrl' => false,
        ],
        'urlManager' => [
            'enablePrettyUrl' => true,
            'showScriptName' => false,
            'enableStrictParsing' => false,
            'rules' => [
                '<controller:(apply|forum|overpoint|postcomment|posts|topic|user|userprofile|overpoint|event|character)>/create' => '<controller>/create',
                '<controller:(apply|forum|overpoint|postcomment|posts|topic|user|userprofile|overpoint|event|character)>/<action:(update|delete)>/<id:\d+>' => '<controller>/<action>/',
                '<controller:(apply|forum|overpoint|postcomment|posts|topic|user|userprofile|overpoint|event|character)>/<id:\d+>' => '<controller>/view',
                '<controller:(apply|forum|overpoint|postcomment|posts|topic|user|userprofile|overpoint|event|character)>' => '<controller>/index',
            ],
        ],
        'authManager' => [
            'class' => 'yii\rbac\DbManager',
        ],
        'cache' => [
            'class' => 'yii\caching\FileCache',
        ],
    ],
];
