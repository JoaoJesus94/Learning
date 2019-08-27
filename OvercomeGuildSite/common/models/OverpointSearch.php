<?php

namespace common\models;

use Yii;
use yii\base\Model;
use yii\data\ActiveDataProvider;
use common\models\Overpoint;

/**
 * OverpointSearch represents the model behind the search form of `app\models\Overpoint`.
 */
class OverpointSearch extends Overpoint
{
    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['idoverpoint', 'op', 'up', 'priority', 'attendance', 'userprofile_id'], 'integer'],
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
        $query = Overpoint::find();

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
            'idoverpoint' => $this->idoverpoint,
            'op' => $this->op,
            'up' => $this->up,
            'priority' => $this->priority,
            'attendance' => $this->attendance,
            'userprofile_id' => $this->userprofile_id,
        ]);

        return $dataProvider;
    }
}
