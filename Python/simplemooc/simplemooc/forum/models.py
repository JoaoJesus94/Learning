from django.db import models
from django.conf import settings
from taggit.managers import TaggableManager
from django.urls import reverse


class Thread(models.Model):
    title = models.CharField('Title', max_length=100)
    slug = models.SlugField('Identifier', max_length=100, unique=True)
    body = models.TextField('Message')
    author = models.ForeignKey(settings.AUTH_USER_MODEL,
                               verbose_name='Author',
                               related_name='threads',
                               on_delete=models.CASCADE)
    views = models.IntegerField('Views', blank=True, default=0)
    tags = TaggableManager()
    answers = models.IntegerField('Answers', blank=True, default=0)

    created_at = models.DateTimeField('Created at', auto_now_add=True)
    updated_at = models.DateTimeField('Updated at', auto_now=True)

    def __str__(self):
        return self.title

    def get_absolute_url(self):
        return reverse('forum:thread', args=[self.slug])

    class Meta:
        verbose_name = 'Topic'
        verbose_name_plural = 'Topics'
        ordering = ['-updated_at']


class Reply(models.Model):
    reply = models.TextField('Reply')
    author = models.ForeignKey(settings.AUTH_USER_MODEL,
                               verbose_name='Author',
                               related_name='replies',
                               on_delete=models.CASCADE)
    thread = models.ForeignKey(Thread,
                               verbose_name='Topic',
                               related_name='replies',
                               null=True,
                               on_delete=models.CASCADE)
    correct = models.BooleanField('Correct', blank=True, default=False)
    created_at = models.DateTimeField('Created at', auto_now_add=True)
    updated_at = models.DateTimeField('Updated at', auto_now=True)

    def __str__(self):
        return self.reply[:50]

    class Meta:
        verbose_name = 'Reply'
        verbose_name_plural = 'Replies'
        ordering = ['-correct', 'created_at']
