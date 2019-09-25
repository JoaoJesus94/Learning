from django.urls import path, include, re_path
from django.contrib.auth.views import LoginView, LogoutView
from . import views

app_name = 'accounts'

urlpatterns = [
    path('', views.dashboard, name='dashboard'),
    path('login/', LoginView.as_view(template_name='accounts/login.html'), name='login'),
    path('logout/', LogoutView.as_view(next_page='core:home'), name='logout'),
    path('register/', views.register, name='register'),
    path('reset-password/', views.password_reset, name='reset_password'),
    re_path(r'^new-password/(?P<key>\w+)/$', views.password_reset_confirm, name='reset_password_confirm'),
    path('edit/', views.edit, name='edit'),
    path('edit-password/', views.edit_password, name='edit_password')
]
