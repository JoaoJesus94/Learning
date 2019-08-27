<?php

namespace common\models;

use Yii;
use yii\base\Model;
use yii\data\ActiveDataProvider;
use common\models\Apply;

/**
 * ApplySearch represents the model behind the search form of `app\models\Apply`.
 */
class ApplySearch extends Apply
{
    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['id', 'age', 'user_id'], 'integer'],
            [['status', 'name', 'mainSpec', 'offSpec', 'class', 'race', 'armory', 'wowHeroes', 'logs', 'uiScreen', 'experience', 'availableTime', 'objective', 'knownPeople'], 'safe'],
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
        $query = Apply::find();

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
            'id' => $this->id,
            'age' => $this->age,
            'user_id' => $this->user_id,
        ]);

        $query->andFilterWhere(['like', 'status', $this->status])
            ->andFilterWhere(['like', 'name', $this->name])
            ->andFilterWhere(['like', 'mainSpec', $this->mainSpec])
            ->andFilterWhere(['like', 'offSpec', $this->offSpec])
            ->andFilterWhere(['like', 'class', $this->class])
            ->andFilterWhere(['like', 'race', $this->race])
            ->andFilterWhere(['like', 'armory', $this->armory])
            ->andFilterWhere(['like', 'wowHeroes', $this->wowHeroes])
            ->andFilterWhere(['like', 'logs', $this->logs])
            ->andFilterWhere(['like', 'uiScreen', $this->uiScreen])
            ->andFilterWhere(['like', 'experience', $this->experience])
            ->andFilterWhere(['like', 'availableTime', $this->availableTime])
            ->andFilterWhere(['like', 'objective', $this->objective])
            ->andFilterWhere(['like', 'knownPeople', $this->knownPeople]);

        return $dataProvider;
    }
}
