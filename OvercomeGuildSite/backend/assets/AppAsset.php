<?php

namespace backend\assets;

use yii\web\AssetBundle;

/**
 * Main backend application asset bundle.
 */
class AppAsset extends AssetBundle
{
    public $basePath = '@webroot';
    public $baseUrl = '@web';
    public $css = [
        'css/site.css',
    ];
    public $js = [
        'js/javascript.js',
        'js/calendar.js'
    ];
    public $depends = [
        'yii\web\YiiAsset',

        'yii\web\JqueryAsset',

        'yii\bootstrap\BootstrapAsset',

        'yii\bootstrap\BootstrapPluginAsset'
    ];
}