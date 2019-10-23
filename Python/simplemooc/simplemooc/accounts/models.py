import re
from django.db import models
from django.core import validators
from django.contrib.auth.models import (AbstractBaseUser, PermissionsMixin, UserManager)
from django.conf import settings


class User(AbstractBaseUser, PermissionsMixin):
    username = models.CharField('Username',
                                max_length=30,
                                unique=True,
                                validators=[validators.RegexValidator(re.compile('^[\w.@+-]+$'),
                                                                      'Username can only contain letter, digit or '
                                                                      '@ / . / + / - / _',
                                                                      'invalid')]
                                )
    email = models.EmailField('E-mail', unique=True)
    name = models.CharField('Name', max_length=100, blank=True)
    is_active = models.BooleanField('Active', blank=True, default=True)
    is_staff = models.BooleanField('Staff status', blank=True, default=False)
    date_joined = models.DateTimeField('Date joined', auto_now_add=True)

    objects = UserManager()

    USERNAME_FIELD = 'username'
    REQUIRED_FIELDS = ['email']

    def __str__(self):
        return self.name or self.username

    def get_short_name(self):
        return self.username

    def get_full_name(self):
        return str(self)

    class Meta:
        verbose_name = 'User'
        verbose_name_plural = 'Users'


class PasswordReset(models.Model):
    user = models.ForeignKey(settings.AUTH_USER_MODEL,
                             verbose_name='User',
                             on_delete=models.CASCADE,
                             related_name='resets')
    key = models.CharField('key',
                           max_length=100,
                           unique=True)
    created_at = models.DateTimeField('Created at',
                                      auto_now_add=True)
    confirmed = models.BooleanField('Confirmed',
                                    default=False,
                                    blank=True)

    def __str__(self):
        return '{0} - {1}'.format(self.user, self.created_at)

    class Meta:
        verbose_name = 'New Password'
        verbose_name_plural = 'New Passwords'
        ordering = ['-created_at']
