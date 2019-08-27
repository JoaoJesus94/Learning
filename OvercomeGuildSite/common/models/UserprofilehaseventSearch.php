<?php

namespace common\models;

use yii\base\Model;
use yii\data\ActiveDataProvider;
use common\models\Userprofilehasevent;

/**
 * UserprofilehaseventSearch represents the model behind the search form of `common\models\Userprofilehasevent`.
 */
class UserprofilehaseventSearch extends Userprofilehasevent
{
    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['userprofile_id', 'event_id'], 'integer'],
            [['attending', 'role'], 'safe'],
        ];
    }

    /**
     * {@inheritdoc}
     */
    public function scenarios()
    {
        // bypass scenarios() implementation in the parent class
        return Model::scenarios();
    }

    /**
     * Creates data provider instance with search query applied
     *
     * @param array $params
     *
     * @return ActiveDataProvider
     */
    public function search($params)
    {
        $query = Userprofilehasevent::find();

        // add conditions that should always apply here

        $dataProvider = new ActiveDataProvider([
            'query' => $query,
        ]);

        $this->load($params);

        if (!$this->validate()) {
            // uncomment the following line if you do not want to return any records when validation fails
            // $query->where('0=1');
            return $dataProvider;
        }

        // grid filtering conditions
        $query->andFilterWhere([
            'userprofile_id' => $this->userprofile_id,
            'event_id' => $this->event_id,
        ]);

        $query->andFilterWhere(['like', 'attending', $this->attending])
            ->andFilterWhere(['like', 'role', $this->role]);

        return $dataProvider;
    }
}
