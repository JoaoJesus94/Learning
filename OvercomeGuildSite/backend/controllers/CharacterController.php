<?php

namespace backend\controllers;

use common\models\CharacterSearch;
use common\models\Userprofile;
use Yii;
use common\models\Character;
use yii\helpers\Json;
use yii\web\Controller;
use yii\web\NotFoundHttpException;
use yii\filters\VerbFilter;

/**
 * CharacterController implements the CRUD actions for Character model.
 */
class CharacterController extends Controller
{
    /**
     * {@inheritdoc}
     */
    public function behaviors()
    {
        return [
            'verbs' => [
                'class' => VerbFilter::className(),
                'actions' => [
                    'delete' => ['POST'],
                ],
            ],
        ];
    }

    /**
     * Lists all Character models.
     * @return mixed
     */
    public function actionIndex()
    {
        $searchModel = new CharacterSearch();
        $dataProvider = $searchModel->search(Yii::$app->request->queryParams);

        return $this->render('index', [
            'searchModel' => $searchModel,
            'dataProvider' => $dataProvider,
        ]);
    }

    /**
     * Displays a single Character model.
     * @param integer $id
     * @return mixed
     * @throws NotFoundHttpException if the model cannot be found
     */
    public function actionView($id)
    {
        return $this->render('view', [
            'model' => $this->findModel($id),
        ]);
    }

    public function actionClasses()
    {
        if (isset($_POST['depdrop_parents'])) {
            $parents = $_POST['depdrop_parents'];
            if ($parents != null) {
                if ($parents[0] == 'Draenei') {
                    $out = [
                        ['id' => 'Death Knight', 'name' => 'Death Knight', 'src' => 'https://wow.zamimg.com/images/wow/icons/small/class_hunter.jpg'],
                        ['id' => 'Hunter', 'name' => 'Hunter'],
                        ['id' => 'Mage', 'name' => 'Mage'],
                        ['id' => 'Monk', 'name' => 'Monk'],
                        ['id' => 'Paladin', 'name' => 'Paladin'],
                        ['id' => 'Priest', 'name' => 'Priest'],
                        ['id' => 'Shaman', 'name' => 'Shaman'],
                        ['id' => 'Warrior', 'name' => 'Warrior'],
                    ];
                }
                if ($parents[0] == 'Dwarf') {
                    $out = [
                        ['id' => 'Death Knight', 'name' => 'Death Knight'],
                        ['id' => 'Hunter', 'name' => 'Hunter'],
                        ['id' => 'Mage', 'name' => 'Mage'],
                        ['id' => 'Monk', 'name' => 'Monk'],
                        ['id' => 'Paladin', 'name' => 'Paladin'],
                        ['id' => 'Priest', 'name' => 'Priest'],
                        ['id' => 'Rogue', 'name' => 'Rogue'],
                        ['id' => 'Shaman', 'name' => 'Shaman'],
                        ['id' => 'Warlock', 'name' => 'Warlock'],
                        ['id' => 'Warrior', 'name' => 'Warrior']
                    ];
                }
                if ($parents[0] == 'Gnome') {
                    $out = [
                        ['id' => 'Death Knight', 'name' => 'Death Knight'],
                        ['id' => 'Hunter', 'name' => 'Hunter'],
                        ['id' => 'Mage', 'name' => 'Mage'],
                        ['id' => 'Monk', 'name' => 'Monk'],
                        ['id' => 'Priest', 'name' => 'Priest'],
                        ['id' => 'Rogue', 'name' => 'Rogue'],
                        ['id' => 'Warlock', 'name' => 'Warlock'],
                        ['id' => 'Warrior', 'name' => 'Warrior']
                    ];
                }
                if ($parents[0] == 'Human') {
                    $out = [
                        ['id' => 'Death Knight', 'name' => 'Death Knight'],
                        ['id' => 'Hunter', 'name' => 'Hunter'],
                        ['id' => 'Mage', 'name' => 'Mage'],
                        ['id' => 'Monk', 'name' => 'Monk'],
                        ['id' => 'Paladin', 'name' => 'Paladin'],
                        ['id' => 'Priest', 'name' => 'Priest'],
                        ['id' => 'Rogue', 'name' => 'Rogue'],
                        ['id' => 'Warlock', 'name' => 'Warlock'],
                        ['id' => 'Warrior', 'name' => 'Warrior']
                    ];
                }
                if ($parents[0] == 'Night Elf') {
                    $out = [
                        ['id' => 'Death Knight', 'name' => 'Death Knight'],
                        ['id' => 'Demon Hunter', 'name' => 'Demon Hunter'],
                        ['id' => 'Druid', 'name' => 'Druid'],
                        ['id' => 'Hunter', 'name' => 'Hunter'],
                        ['id' => 'Mage', 'name' => 'Mage'],
                        ['id' => 'Monk', 'name' => 'Monk'],
                        ['id' => 'Priest', 'name' => 'Priest'],
                        ['id' => 'Rogue', 'name' => 'Rogue'],
                        ['id' => 'Warrior', 'name' => 'Warrior']
                    ];
                }
                if ($parents[0] == 'Worgen') {
                    $out = [
                        ['id' => 'Death Knight', 'name' => 'Death Knight'],
                        ['id' => 'Druid', 'name' => 'Druid'],
                        ['id' => 'Hunter', 'name' => 'Hunter'],
                        ['id' => 'Mage', 'name' => 'Mage'],
                        ['id' => 'Priest', 'name' => 'Priest'],
                        ['id' => 'Rogue', 'name' => 'Rogue'],
                        ['id' => 'Warlock', 'name' => 'Warlock'],
                        ['id' => 'Warrior', 'name' => 'Warrior']
                    ];
                }
                if ($parents[0] == 'Pandaren') {
                    $out = [
                        ['id' => 'Hunter', 'name' => 'Hunter'],
                        ['id' => 'Mage', 'name' => 'Mage'],
                        ['id' => 'Monk', 'name' => 'Monk'],
                        ['id' => 'Priest', 'name' => 'Priest'],
                        ['id' => 'Rogue', 'name' => 'Rogue'],
                        ['id' => 'Shaman', 'name' => 'Shaman'],
                        ['id' => 'Warrior', 'name' => 'Warrior']
                    ];
                }
                if ($parents[0] == 'Dark Iron Dwarf') {
                    $out = [
                        ['id' => 'Hunter', 'name' => 'Hunter'],
                        ['id' => 'Mage', 'name' => 'Mage'],
                        ['id' => 'Monk', 'name' => 'Monk'],
                        ['id' => 'Paladin', 'name' => 'Paladin'],
                        ['id' => 'Priest', 'name' => 'Priest'],
                        ['id' => 'Rogue', 'name' => 'Rogue'],
                        ['id' => 'Shaman', 'name' => 'Shaman'],
                        ['id' => 'Warlock', 'name' => 'Warlock'],
                        ['id' => 'Warrior', 'name' => 'Warrior']
                    ];
                }
                if ($parents[0] == 'Kul Tiran Human') {
                    $out = [
                        ['id' => 'Druid', 'name' => 'Druid'],
                        ['id' => 'Hunter', 'name' => 'Hunter'],
                        ['id' => 'Mage', 'name' => 'Mage'],
                        ['id' => 'Monk', 'name' => 'Monk'],
                        ['id' => 'Priest', 'name' => 'Priest'],
                        ['id' => 'Rogue', 'name' => 'Rogue'],
                        ['id' => 'Shaman', 'name' => 'Shaman'],
                        ['id' => 'Warrior', 'name' => 'Warrior']
                    ];
                }
                if ($parents[0] == 'Lightforged Draenei') {
                    $out = [
                        ['id' => 'Hunter', 'name' => 'Hunter'],
                        ['id' => 'Mage', 'name' => 'Mage'],
                        ['id' => 'Paladin', 'name' => 'Paladin'],
                        ['id' => 'Priest', 'name' => 'Priest'],
                        ['id' => 'Warrior', 'name' => 'Warrior']
                    ];
                }
                if ($parents[0] == 'Void Elf') {
                    $out = [
                        ['id' => 'Hunter', 'name' => 'Hunter'],
                        ['id' => 'Mage', 'name' => 'Mage'],
                        ['id' => 'Monk', 'name' => 'Monk'],
                        ['id' => 'Priest', 'name' => 'Priest'],
                        ['id' => 'Rogue', 'name' => 'Rogue'],
                        ['id' => 'Warlock', 'name' => 'Warlock'],
                        ['id' => 'Warrior', 'name' => 'Warrior']
                    ];
                }
                echo Json::encode(['output' => $out, 'selected' => '']);
                return;
            }
        }
        echo Json::encode(['output' => '', 'selected' => '']);
    }

    public function actionSpecializations()
    {
        if (isset($_POST['depdrop_parents'])) {
            $parents = $_POST['depdrop_parents'];
            if ($parents != null) {
                if ($parents[0] == 'Warrior') {
                    $out = [
                        ['id' => 'Arms', 'name' => 'Arms'],
                        ['id' => 'Fury', 'name' => 'Fury'],
                        ['id' => 'Protection', 'name' => 'Protection'],
                    ];
                }
                if ($parents[0] == 'Paladin') {
                    $out = [
                        ['id' => 'Holy', 'name' => 'Holy'],
                        ['id' => 'Protection', 'name' => 'Protection'],
                        ['id' => 'Retribution', 'name' => 'Retribution'],
                    ];
                }
                if ($parents[0] == 'Hunter') {
                    $out = [
                        ['id' => 'Beast Mastery', 'name' => 'Beast Mastery'],
                        ['id' => 'Marksmanship', 'name' => 'Marksmanship'],
                        ['id' => 'Survival', 'name' => 'Survival'],
                    ];
                }
                if ($parents[0] == 'Rogue') {
                    $out = [
                        ['id' => 'Assassination', 'name' => 'Assassination'],
                        ['id' => 'Outlaw', 'name' => 'Outlaw'],
                        ['id' => 'Subtlety', 'name' => 'Subtlety'],
                    ];
                }
                if ($parents[0] == 'Priest') {
                    $out = [
                        ['id' => 'Discipline', 'name' => 'Discipline'],
                        ['id' => 'Holy', 'name' => 'Holy'],
                        ['id' => 'Shadow', 'name' => 'Shadow'],
                    ];
                }
                if ($parents[0] == 'Death Knight') {
                    $out = [
                        ['id' => 'Blood', 'name' => 'Blood'],
                        ['id' => 'Frost', 'name' => 'Frost'],
                        ['id' => 'Unholy', 'name' => 'Unholy'],
                    ];
                }
                if ($parents[0] == 'Shaman') {
                    $out = [
                        ['id' => 'Elemental', 'name' => 'Elemental'],
                        ['id' => 'Enhancement', 'name' => 'Enhancement'],
                        ['id' => 'Restoration', 'name' => 'Restoration'],
                    ];
                }
                if ($parents[0] == 'Mage') {
                    $out = [
                        ['id' => 'Arcane', 'name' => 'Arcane'],
                        ['id' => 'Fire', 'name' => 'Fire'],
                        ['id' => 'Frost', 'name' => 'Frost'],
                    ];
                }
                if ($parents[0] == 'Warlock') {
                    $out = [
                        ['id' => 'Affliction', 'name' => 'Affliction'],
                        ['id' => 'Demonology', 'name' => 'Demonology'],
                        ['id' => 'Destruction', 'name' => 'Destruction'],
                    ];
                }
                if ($parents[0] == 'Monk') {
                    $out = [
                        ['id' => 'Brewmaster', 'name' => 'Brewmaster'],
                        ['id' => 'Mistweaver', 'name' => 'Mistweaver'],
                        ['id' => 'Windwalker', 'name' => 'Windwalker'],
                    ];
                }
                if ($parents[0] == 'Druid') {
                    $out = [
                        ['id' => 'Balance', 'name' => 'Balance'],
                        ['id' => 'Feral', 'name' => 'Feral'],
                        ['id' => 'Guardian', 'name' => 'Guardian'],
                        ['id' => 'Restoration', 'name' => 'Restoration'],
                    ];
                }
                if ($parents[0] == 'Demon Hunter') {
                    $out = [
                        ['id' => 'Havoc', 'name' => 'Havoc'],
                        ['id' => 'Vengeance', 'name' => 'Vengeance'],
                    ];
                }
                echo Json::encode(['output' => $out, 'selected' => '']);
                return;
            }
        }
        echo Json::encode(['output' => '', 'selected' => '']);
    }

    /**
     * Creates a new Character model.
     * If creation is successful, the browser will be redirected to the 'view' page.
     * @return mixed
     */
    public function actionCreate()
    {
        $model = new Character();

        $userprofile = Userprofile::findOne(['user_id' => Yii::$app->user->getId()]);

        $model->User_id = $userprofile['id'];

        if ($model->load(Yii::$app->request->post()) && $model->save()) {
            return $this->redirect(['userprofile/'.$userprofile->getId()]);
        }

        $races = [
            'Races' =>
                ['Draenei' => 'Draenei', 'Dwarf' => 'Dwarf', 'Gnome' => 'Gnome', 'Human' => 'Human',
                    'Night Elf' => 'Night Elf', 'Worgen' => 'Worgen', 'Pandaren' => 'Pandaren'],
            'Allied Races' =>
                ['Dark Iron Dwarf' => 'Dark Iron Dwarf', 'Kul Tiran Human' => 'Kul Tiran Human',
                    'Lightforged Draenei' => 'Lightforged Draenei', 'Void Elf' => 'Void Elf']
        ];

        return $this->render('create', [
            'model' => $model,
            'races' => $races
        ]);
    }

    /**
     * Updates an existing Character model.
     * If update is successful, the browser will be redirected to the 'view' page.
     * @param integer $id
     * @return mixed
     * @throws NotFoundHttpException if the model cannot be found
     */
    public function actionUpdate($id)
    {
        $model = $this->findModel($id);
        if (Yii::$app->user->can('editCharacter', ['userCharacter' => $model])) {
            if ($model->load(Yii::$app->request->post()) && $model->save()) {
                return $this->redirect(['view', 'id' => $model->id]);
            }

            return $this->render('update', [
                'model' => $model,
            ]);
        } else {
            Yii::$app->session->setFlash('error', 'You are not allowed!');
            $id = $model->user->id;
            return $this->redirect(['/userprofile/'.$id]);
        }
    }

    /**
     * Deletes an existing Character model.
     * If deletion is successful, the browser will be redirected to the 'index' page.
     * @param integer $id
     * @return mixed
     * @throws NotFoundHttpException if the model cannot be found
     */
    public function actionDelete($id, $userprofile_id)
    {
        $model = $this->findModel($id);
        $userprofile = Userprofile::findOne(['id' => $userprofile_id]);
        if (Yii::$app->user->can('deleteCharacter', ['userCharacter' => $model])) {
            $model->delete();
            Yii::$app->session->setFlash('success', 'Deleted with success');
            return $this->redirect(['userprofile/' . $userprofile->id]);
        } else {
            Yii::$app->session->setFlash('error', 'That\'s not your character!');
            $id = $model->user->id;
            return $this->redirect(['/userprofile/'.$id]);
        }
    }

    /**
     * Finds the Character model based on its primary key value.
     * If the model is not found, a 404 HTTP exception will be thrown.
     * @param integer $id
     * @return Character the loaded model
     * @throws NotFoundHttpException if the model cannot be found
     */
    protected function findModel($id)
    {
        if (($model = Character::findOne($id)) !== null) {
            return $model;
        }

        throw new NotFoundHttpException('The requested page does not exist.');
    }
}
