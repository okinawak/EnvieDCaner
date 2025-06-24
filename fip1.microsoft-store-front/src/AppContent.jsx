// AppContent.js
import React, { useState, useEffect } from 'react';
import { Routes, Route, useNavigate, Navigate } from 'react-router-dom';
import Navbar from './components/Navbar';
import Home from './pages/Home';
import MyGames from './pages/MyGames';
import GameDetails from './pages/GameDetails';
import Profile from './pages/Profile';
import Signup from './pages/Signup';
import Login from './pages/Login';
import Creation from './pages/Creation';

function AppContent() {
  const navigate = useNavigate();
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [user, setUser] = useState(null);
  const [games, setGames] = useState([]);
  const [isOnline, setIsOnline] = useState(navigator.onLine);
  const [showBanner, setShowBanner] = useState(false);
  const [bannerMessage, setBannerMessage] = useState('');

  useEffect(() => {
    const handleOnline = () => {
      setIsOnline(true);
      showTemporaryBanner('Connexion rétablie', 'success');
    };
    const handleOffline = () => {
      setIsOnline(false);
      showTemporaryBanner('Connexion perdue', 'error');
    };

    window.addEventListener('online', handleOnline);
    window.addEventListener('offline', handleOffline);
    return () => {
      window.removeEventListener('online', handleOnline);
      window.removeEventListener('offline', handleOffline);
    };
  }, []);

  const showTemporaryBanner = (message, type) => {
    setBannerMessage({ text: message, type });
    setShowBanner(true);
    setTimeout(() => setShowBanner(false), 4000);
  };

  useEffect(() => {
    if (isOnline) {
      fetch('http://localhost:8080/api/applications')
        .then(res => res.json())
        .then(data => setGames(data))
        .catch(err => console.error('Erreur de chargement des applications :', err));
    }
  }, [isOnline]);

  const handleLogin = (userData) => {
    setUser(userData);
    setIsLoggedIn(true);
  };

  const handleLogout = () => {
    setIsLoggedIn(false);
    setUser(null);
    navigate('/');
  };

  const handleUserUpdate = async (updatedUser) => {
    try {
      const response = await fetch(`http://localhost:8080/api/utilisateurs/${user.id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          pseudo: updatedUser.username,
          email: updatedUser.email,
        }),
      });

      if (response.ok) {
        const updatedUserFromServer = await response.json();
        setUser(prevUser => ({ ...prevUser, ...updatedUserFromServer }));
      } else {
        console.error("Erreur lors de la mise à jour du profil.");
      }
    } catch (error) {
      console.error("Erreur réseau :", error);
    }
  };

  const menuItems = [
    { path: '/', label: 'Accueil', icon: 'home' },
    { path: '/my-games', label: 'Mes Jeux', icon: 'library' },
    { path: '/profile', label: 'Mon Profil', icon: 'user', requiresAuth: true },
    { path: '/creation', label: 'Créer', icon: 'plus', requiresAuth: true, creatorOnly: true }
  ];

  const filteredMenuItems = menuItems.filter(item => {
    if (item.requiresAuth && !isLoggedIn) return false;
    if (item.creatorOnly && user?.statut !== 'createur') return false;
    return true;
  });
console.log(user)
  if (!isOnline && games.length === 0) {
    return (
      <div style={{
        height: '100vh',
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: '#fafafa',
        textAlign: 'center',
        padding: '2rem',
      }}>
        <h1 style={{ fontSize: '2rem', color: '#cc0000' }}>
          Problème d'accès à internet,<br /> veuillez vérifier votre connexion.
        </h1>
      </div>
    );
  }

  return (
    <div className="app">
      {showBanner && (
        <div
          style={{
            position: 'fixed',
            top: 0,
            width: '100%',
            padding: '1rem',
            backgroundColor: bannerMessage.type === 'error' ? '#ff4d4f' : '#52c41a',
            color: 'white',
            textAlign: 'center',
            zIndex: 1000,
            transition: 'opacity 0.3s ease-in-out',
          }}
        >
          {bannerMessage.text}
        </div>
      )}

      <Navbar isLoggedIn={isLoggedIn} onLogout={handleLogout} menuItems={filteredMenuItems} />
      <div className="content">
        <Routes>
          <Route path="/" element={<Home games={games} isLoggedIn={isLoggedIn} onLogin={handleLogin} />} />
          <Route
            path="/my-games"
            element={<MyGames user={user} games={games.filter(game => user?.ownedGames?.includes(game.id))} />}
          />
          <Route path="/signup" element={<Signup />} />
          <Route path="/login" element={<Login onLogin={handleLogin} />} />
          <Route
            path="/game/:id"
            element={<GameDetails games={games} isLoggedIn={isLoggedIn} user={user} isOnline={isOnline} />}
          />
          <Route
            path="/profile"
            element={<Profile isLoggedIn={isLoggedIn} user={user} onUserUpdate={handleUserUpdate} />}
          />
          <Route
            path="/creation"
            element={isLoggedIn && user?.statut === 'createur' ? <Creation /> : <Navigate to="/" />}
          />
        </Routes>
      </div>
    </div>
  );
}

export default AppContent;
