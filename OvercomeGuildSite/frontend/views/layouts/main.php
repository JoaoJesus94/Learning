<?php

/* @var $this \yii\web\View */

/* @var $content string */

use yii\helpers\Html;
use yii\helpers\Url;
use frontend\assets\AppAsset;
use common\widgets\Alert;
use kartik\icons\Icon;
use \common\models\User;

$userId = Yii::$app->user->getId();
$userModel = new User();
$user = $userModel->getUser($userId);

Icon::map($this, Icon::FAS);
Icon::map($this, Icon::FA);
Icon::map($this, Icon::EL);
Icon::map($this, Icon::WHHG);

AppAsset::register($this);
?>

<?php $this->beginPage() ?>
<!DOCTYPE html>
<html lang="<?= Yii::$app->language ?>">
<head>
    <link rel="shortcut icon" href="favicon.ico" type="image/x-icon"/>
    <meta charset="<?= Yii::$app->charset ?>">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <?= Html::csrfMetaTags() ?>
    <title><?= Html::encode($this->title) ?></title>
    <?php $this->head() ?>
</head>
<body>
<?php $this->beginBody() ?>

<div class="wrap background-img">
    <div>
        <?php $logo_img = Yii::$app->imageresize->getUrl("@frontend/web/img/Logo.png", 600, 600, 'inset', 100, "Logo_resize.png") ?>
        <?= Html::img($logo_img, ['class' => 'header-center', 'alt' => '']); ?>


        <div class="myContainer">
            <div class="row">
                <div id="navbar" class="col-md-2">
                    <div class="profile-sidebar transparent-background">
                        <!-- SIDEBAR USERPIC -->
                        <div class="profile-userpic" style="text-align: center">
                            <?php
                            if (!Yii::$app->user->isGuest) {
                                echo Html::img(Yii::$app->request->baseUrl . '/img/Logo.png', ['class' => 'img-responsive', 'alt' => '']);
                            } else {
                                echo Html::a(Icon::show('sign-in-alt', ['framework' => Icon::FAS]) . 'Login', ['site/login'], ['class' => 'btn', 'title' => 'Login']);
                                echo Html::a(Icon::show('register', ['framework' => Icon::FAS]) . 'Register', ['site/signup'], ['class' => 'btn', 'title' => 'Register']);
                            } ?>
                        </div>
                        <!-- END SIDEBAR USERPIC -->
                        <!-- SIDEBAR USER TITLE -->
                        <div class="profile-usertitle">
                            <div class="profile-usertitle-name">
                                <?php
                                if (!Yii::$app->user->isGuest) {
                                    if ($user->getUserProfile() != null) {
                                        echo $user->getUserProfile()->displayName;
                                    } else {
                                        echo $user->username;
                                    }
                                } ?>
                            </div>
                            <div class="profile-usertitle-job">
                                <?php
                                if (!Yii::$app->user->isGuest) {
                                    if ($user->getUserProfile() != null) {
                                        echo $user->getUserProfile()->rank;
                                    } else {
                                        echo 'Guest';
                                    }
                                } else {
                                    echo 'Register to know more';
                                } ?>
                            </div>
                        </div>
                        <!-- END SIDEBAR USER TITLE -->
                        <div class="profile-userbuttons">
                            <?php
                            if (!Yii::$app->user->isGuest) {
                                echo Html::a('<button class="btn btn-danger btn-sm">Logout</button>', ['site/logout'], ['data-method' => 'post']);
                            } ?>
                        </div>

                        <!-- SIDEBAR MENU -->
                        <div class="profile-usermenu">
                            <ul class="nav">
                                <li>
                                    <?= Html::a(Icon::show('home', ['framework' => Icon::FAS]) . ' Home', Url::to(['/'])) ?>
                                </li>
                                <?php
                                if (!Yii::$app->user->isGuest) {
                                    if ($user->getUserProfile() != null) {
                                        echo '<li>';
                                        echo Html::a(Icon::show('user', ['framework' => Icon::FAS]) . ' Profile', Url::to(['userprofile/' . $user->getUserProfile()->id]));
                                        echo '</li>';
                                    } ?>
                                    <?php if ($user->getApply() == null) {
                                        echo '<li>';
                                        echo Html::a(Icon::show('ok', ['framework' => Icon::WHHG]) . 'Create Apply', Url::to(['apply/create']));
                                        echo '</li>';
                                    } elseif ($user->getApply()->status == 'OPEN') {
                                        echo '<li>';
                                        echo Html::a(Icon::show('ok', ['framework' => Icon::WHHG]) . 'View Apply', Url::to(['apply/' . $user->getApply()->id]));
                                        echo '</li>';
                                    } elseif ($user->getApply()->status == 'REFUSED') {
                                        echo '<li>';
                                        echo Html::a(Icon::show('ok', ['framework' => Icon::WHHG]). 'Reopen Apply', Url::to(['apply/update/' . $user->getApply()->id]));
                                        echo '</li>';
                                    }?>
                                    <li>
                                        <?= Html::a(Icon::show('group', ['framework' => Icon::EL]) . 'Roster', Url::to(['userprofile/'])) ?>
                                    </li>
                                    <li>
                                        <?= Html::a(Icon::show('warcraft', ['framework' => Icon::WHHG]) . 'Characters', Url::to(['character/'])); ?>
                                    </li>
                                    <?php if ($user->getUserProfile() != null) {
                                        echo '<li>';
                                        echo Html::a(Icon::show('th', ['framework' => Icon::FAS]) . ' OP Table', Url::to(['overpoint/']));
                                        echo '</li>';
                                        echo '<li>';
                                        echo  Html::a(Icon::show('calendar', ['framework' => Icon::EL]) . 'Events', Url::to(['event/']));
                                        echo '</li>';
                                    } ?>
                                    <li>
                                        <?= Html::a(Icon::show('forumsalt', ['framework' => Icon::WHHG]) . 'Forum', Url::to(['forum/'])) ?>
                                    </li>
                                    <li>
                                        <?= Html::a(Icon::show('newspaper', ['framework' => Icon::FAS]) . ' News', Url::to(['news/'])) ?>
                                    </li>
                                <?php } ?>


                            </ul>
                        </div>
                        <!-- END MENU -->
                    </div>
                </div>

                <div id="navbar1" class="col-md-2 right-nav-float-right">
                    <a class="header-center" href="https://www.wowprogress.com/guild/eu/grim-batol/overcome">
                        <img class="header-center" alt="WoW Guild Rankings"
                             src="https://wowprogress.com/guild_img/695204/out/type.site"
                             border="1"/>
                    </a>
                    <br>
                    <br>
                    <p style="text-align: center; font-size: x-large; color: dimgrey">Recruitment</p>
                    <?php $recruitment_img = Yii::$app->imageresize->getUrl("@frontend/web/img/Recruitment.jpg", 200, 400, 'inset', 100, "Logo_resize.png") ?>
                    <?= Html::img($recruitment_img, ['class' => 'header-center', 'alt' => '']); ?>
                    <br>
                    <br>
                    <p style="text-align: center; font-size: x-large; color: dimgrey">Progress</p>
                    <?php $progress_img = Yii::$app->imageresize->getUrl("@frontend/web/img/Progress.jpg", 300, 250, 'inset', 100, "Logo_resize.png") ?>
                    <?= Html::img($progress_img, ['class' => 'header-center', 'alt' => '']); ?>


                </div>
                <div id="content" class="col-md-8 div-border-white">
                    <div class="profile-content transparent-background">

                        <?= Alert::widget() ?>
                        <?= $content ?>
                    </div>
                </div>
            </div>
        </div>

        <br>
        <br>
    </div>


</div>

<footer class="footer">
    <div class="container">
        <p class="pull-left">&copy; <?= Html::encode(Yii::$app->name) ?> <?= date('Y') ?></p>

        <p class="pull-right"><?= Yii::powered() ?></p>
    </div>
</footer>

<?php $this->endBody() ?>
</body>
</html>
<?php $this->endPage() ?>
