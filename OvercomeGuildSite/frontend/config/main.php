<?php
$params = array_merge(
    require __DIR__ . '/../../common/config/params.php',
    require __DIR__ . '/../../common/config/params-local.php',
    require __DIR__ . '/params.php',
    require __DIR__ . '/params-local.php'
);

return [
    'id' => 'app-frontend',
    'basePath' => dirname(__DIR__),
    'bootstrap' => ['log'],
    'controllerNamespace' => 'frontend\controllers',
    'components' => [
        'request' => [
            'csrfParam' => '_csrf-frontend',
            'enableCsrfValidation' => false,
            'parsers' => [
                'application/json' => 'yii\web\JsonParser',
            ]
        ],
        'user' => [
            'identityClass' => 'common\models\User',
            'enableAutoLogin' => true,
            'identityCookie' => ['name' => '_identity-frontend', 'httpOnly' => true],
        ],
        'session' => [
            // this is the name of the session cookie used for login on the frontend
            'name' => 'advanced-frontend',
        ],
        'log' => [
            'traceLevel' => YII_DEBUG ? 3 : 0,
            'targets' => [
                [
                    'class' => 'yii\log\FileTarget',
                    'levels' => ['error', 'warning'],
                ],
            ],
        ],
        'errorHandler' => [
            'errorAction' => 'site/error',
        ],

        'urlManager' => [
            'enablePrettyUrl' => true,
            'showScriptName' => false,
            'rules' => [
                [
                    'class' => 'yii\rest\UrlRule',
                    'controller' => 'v1/apply',
                    'pluralize' => false,
                    'extraPatterns' =>
                    [
                        'GET userapply/{userid}' => 'userapply',
                    ],
                    'tokens' =>
                    [
                        '{id}' => '<id:\\d+>',
                        '{userid}' => '<userid:\\d+>',
                    ]
                ],
                [
                    'class' => 'yii\rest\UrlRule',
                    'controller' => 'v1/character',
                    'pluralize' => false,
                    'extraPatterns' =>
                    [
                        'GET char/{userid}' => 'char',
                    ],
                    'tokens' =>
                    [
                        '{id}' => '<id:\\d+>',
                        '{userid}' => '<userid:\\d+>',
                    ],
                ],
                [
                    'class' => 'yii\rest\UrlRule',
                    'controller' => 'v1/userprofilehasevent',
                    'pluralize' => false,
                    'extraPatterns' =>
                    [
                        'GET events/{idevent}' => 'events',
                        'GET going/{idevent}' => 'going'
                    ],
                    'tokens' =>
                    [
                        '{id}' => '<id:\\d+>',
                        '{idevent}' => '<idevent:\\d+>',
                    ],
                ],
                [
                    'class' => 'yii\rest\UrlRule',
                    'controller' => 'v1/event',
                    'pluralize' => false,
                ],
                [
                    'class' => 'yii\rest\UrlRule',
                    'controller' => 'v1/logs',
                    'pluralize' => false,
                ],
                [
                    'class' => 'yii\rest\UrlRule',
                    'controller' => 'v1/newscomment',
                    'pluralize' => false,
                    'extraPatterns' =>
                    [
                        'GET newscomments/{idnew}' => 'newscomments',
                    ],
                    'tokens' =>
                    [
                        '{id}' => '<id:\\d+>',
                        '{idnew}' => '<idnew:\\d+>',
                    ],
                ],
                [
                    'class' => 'yii\rest\UrlRule',
                    'controller' => 'v1/news',
                    'pluralize' => false,
                ],
                [
                    'class' => 'yii\rest\UrlRule',
                    'controller' => 'v1/overpoint',
                    'pluralize' => false,
                    'extraPatterns' =>
                    [
                        'GET overpoint' => 'overpoint',
                    ],
                ],
                [
                    'class' => 'yii\rest\UrlRule',
                    'controller' => 'v1/postcomment',
                    'pluralize' => false,
                    'extraPatterns' =>
                    [
                        'GET postcomment/{postid}' => 'postcomment',
                    ],
                    'tokens' =>
                    [
                        '{id}' => '<id:\\d+>',
                        '{postid}' => '<postid:\\d+>',
                    ],
                ],
                [
                    'class' => 'yii\rest\UrlRule',
                    'controller' => 'v1/posts',
                    'pluralize' => false,
                    'extraPatterns' =>
                    [
                        'GET posts/{topicid}' => 'posts',
                        'DELETE del/{idpost}' => 'del',
                    ],
                    'tokens' =>
                    [
                        '{id}' => '<id:\\d+>',
                        '{topicid}' => '<topicid:\\d+>',
                        '{idpost}' => '<idpost:\\d+>',
                    ],
                ],
                [
                    'class' => 'yii\rest\UrlRule',
                    'controller' => 'v1/topic',
                    'pluralize' => false,
                    'extraPatterns' =>
                    [
                        'GET topic' => 'topic',
                        'GET subject' => 'subject',
                        'DELETE del/{idtopic}' => 'del',
                    ],
                    'tokens' =>
                    [
                        '{id}' => '<id:\\d+>',
                        '{idtopic}' => '<idtopic:\\d+>',
                    ],
                ],
                [
                    'class' => 'yii\rest\UrlRule',
                    'controller' => 'v1/user',
                    'pluralize' => false,
                    'extraPatterns' =>
                    [
                        'POST login' => 'login',
                        'GET roster/{userid}' => 'roster',
                    ],
                    'tokens' =>
                    [
                        '{id}' => '<id:\\d+>',
                        '{userid}' => '<userid:\\d+>',
                    ]
                ],
                [
                    'class' => 'yii\rest\UrlRule',
                    'controller' => 'v1/userprofile',
                    'pluralize' => false,
                    'extraPatterns' =>
                    [
                        'GET profile/{userid}' => 'profile',
                    ],
                    'tokens' =>
                    [
                        '{id}' => '<id:\\d+>',
                        '{userid}' => '<userid:\\d+>',
                    ]
                ],

            ],
        ],
    ],

    'modules' => [
        'v1' => [
            'class' => 'app\modules\v1\Module',
        ],
    ],

    'params' => $params,
];
