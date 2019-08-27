from django.urls import path, re_path
from ..courses import views

app_name = 'courses'

urlpatterns = [
    path('', views.index, name='index'),
    # re_path(r'^(?P<pk>\d+)/$', views.details, name='details')
    re_path(r'^(?P<slug>[\w_-]+)/$', views.details, name='details'),
    re_path(r'^(?P<slug>[\w_-]+)/enrollments/$', views.enrollment, name='enrollment'),
    re_path(r'^(?P<slug>[\w_-]+)/undo-enrollments/$', views.undo_enrollment, name='undo_enrollment'),
    re_path(r'^(?P<slug>[\w_-]+)/announcements/$', views.announcements, name='announcements'),
    re_path(r'^(?P<slug>[\w_-]+)/announcements/(?P<pk>\d+)/$', views.show_announcement, name='show_announcement'),
    re_path(r'^(?P<slug>[\w_-]+)/lessons/$', views.lessons, name='lessons'),
    re_path(r'^(?P<slug>[\w_-]+)/lessons/(?P<pk>\d+)/$', views.lesson, name='lesson'),
    re_path(r'^(?P<slug>[\w_-]+)/materials/(?P<pk>\d+)/$', views.material, name='material')

]