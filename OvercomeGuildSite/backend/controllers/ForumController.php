<?php

namespace backend\controllers;

use common\models\Posts;
use common\models\PostsSearch;
use common\models\Topic;
use common\models\TopicSearch;
use Yii;
use yii\base\Controller;
use yii\data\ActiveDataProvider;
use yii\data\ArrayDataProvider;
use yii\filters\AccessControl;
use yii\filters\VerbFilter;

class ForumController extends Controller
{
    public function behaviors()
    {
        return [
            'access' => [
                'class' => AccessControl::className(),
                'only' => ['index', 'create', 'view', 'update', 'delete'],
                'rules' => [
                    [
                        'actions' => ['index', 'create', 'view', 'update', 'delete'],
                        'allow' => true,
                        'roles' => ['admin'],
                    ],
                ],
            ],
            'verbs' => [
                'class' => VerbFilter::className(),
                'actions' => [
                    'delete' => ['POST'],
                ],
            ],
        ];
    }

    public function actionIndex()
    {
        $topics = Topic::findAll(['Topic_id' => null]);
        $dataProvider = new ArrayDataProvider([
            'allModels' => $topics,
        ]);

        return $this->render('index', [
            'dataProvider' => $dataProvider,
            'topics' => $topics,
        ]);
    }
}
