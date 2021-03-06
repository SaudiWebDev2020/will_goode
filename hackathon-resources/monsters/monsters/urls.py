"""monsters URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/3.1/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""

from django.urls import path, include
from rest_framework import routers
from monster_api import views as monster_views
from user_api import views as user_views

router = routers.DefaultRouter()
router.register(r'monsters', monster_views.MonsterViewSet)

urlpatterns = [
    path(r'api/', include(router.urls)),
    path(r'api/users', user_views.UserList.as_view()),
    path(r'api/sessions', user_views.UserSessions.as_view()),
    path(r'api/users/<int:user_id>', user_views.UserDetails.as_view())
]
