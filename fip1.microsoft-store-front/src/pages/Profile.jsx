// pages/Profile.js
import React, { useState } from 'react';
import { Navigate } from 'react-router-dom';
import { User, Mail, Save, X } from 'lucide-react';
import '../styles/Profile.css';

const Profile = ({ isLoggedIn, user, onUserUpdate }) => {
  const [editMode, setEditMode] = useState(false);
  const [formData, setFormData] = useState({
    username: user ? user.pseudo : '',
    email: user ? user.email : '',
    photo: user ? user.profilePicture :''
  });
  const [errors, setErrors] = useState({});

  // Redirige vers la page d'accueil si l'utilisateur n'est pas connecté
  if (!isLoggedIn || !user) {
    return <Navigate to="/" />;
  }



  const handleEdit = () => {
    setFormData({
      username: user.pseudo,
      email: user.email,
      photo : user.profilePicture
    });
    setEditMode(true);
    setErrors({});
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });

    // Effacer l'erreur lorsque l'utilisateur commence à corriger
    if (errors[name]) {
      setErrors({
        ...errors,
        [name]: null,
      });
    }
  };

  const handleCancel = () => {
    setEditMode(false);
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    // Validation simple
    const newErrors = {};

    if (!formData.username.trim()) {
      newErrors.username = "Le nom d'utilisateur est requis";
    }

    if (!formData.email.trim()) {
      newErrors.email = "L'email est requis";
    } else if (!/\S+@\S+\.\S+/.test(formData.email)) {
      newErrors.email = "Format d'email invalide";
    }

    if (Object.keys(newErrors).length > 0) {
      setErrors(newErrors);
      return;
    }

    // Mise à jour du profil
    onUserUpdate({
      username: formData.username,
      email: formData.email,
    });



    setEditMode(false);
  };

  return (
    <div className="profile-page">
      <div className="profile-container">
        <div className="profile-header">
          <h1>Mon profil</h1>
          {!editMode && (
            <button className="edit-profile-btn" onClick={handleEdit}>
              Modifier
            </button>
          )}
        </div>

        <div className="profile-content">
          <div className="profile-avatar">
            {/* <img src={user.profilePicture} alt="Avatar" /> */}
            <img
              src={`data:image/png;base64,${user.profilePicture}`}
              alt="Avatar"
            />
          </div>

          {editMode ? (
            <form className="profile-form" onSubmit={handleSubmit}>
              <div className="form-group">
                <label htmlFor="username">
                  <User size={16} />
                  Nom d'utilisateur
                </label>
                <input
                  type="text"
                  id="username"
                  name="username"
                  value={formData.username}
                  onChange={handleChange}
                  className={errors.username ? 'error' : ''}
                />
                {errors.username && <div className="error-message">{errors.username}</div>}
              </div>

              <div className="form-group">
                <label htmlFor="email">
                  <Mail size={16} />
                  Email
                </label>
                <input
                  type="email"
                  id="email"
                  name="email"
                  value={formData.email}
                  onChange={handleChange}
                  className={errors.email ? 'error' : ''}
                />
                {errors.email && <div className="error-message">{errors.email}</div>}
              </div>

              <div className="form-actions">
                <button type="submit" className="save-btn">
                  <Save size={16} />
                  Enregistrer
                </button>
                <button type="button" className="cancel-btn" onClick={handleCancel}>
                  <X size={16} />
                  Annuler
                </button>
              </div>
            </form>
          ) : (
            <div className="profile-info">
              <div className="info-group">
                <div className="info-label">
                  <User size={16} />
                  Nom d'utilisateur
                </div>
                <div className="info-value">{user.pseudo}</div>
              </div>

              <div className="info-group">
                <div className="info-label">
                  <Mail size={16} />
                  Email
                </div>
                <div className="info-value">{user.email}</div>
              </div>

              <div className="info-group">
                <div className="info-label">Jeux possédés</div>
                <div className="info-value">{user.ownedGames ? user.ownedGames.length : 0} jeux</div>
              </div>
            </div>
          )}
        </div>

        <div className="account-options">
          <h2>Options du compte</h2>

          <div className="option-group">
            <h3>Préférences de notifications</h3>
            <div className="checkbox-option">
              <input type="checkbox" id="emailNotifications" defaultChecked />
              <label htmlFor="emailNotifications">Recevoir des notifications par email</label>
            </div>
            <div className="checkbox-option">
              <input type="checkbox" id="gameUpdates" defaultChecked />
              <label htmlFor="gameUpdates">Notifications de mises à jour des jeux</label>
            </div>
            <div className="checkbox-option">
              <input type="checkbox" id="specialOffers" />
              <label htmlFor="specialOffers">Offres spéciales et promotions</label>
            </div>
          </div>

          <div className="option-group">
            <h3>Paramètres de confidentialité</h3>
            <div className="checkbox-option">
              <input type="checkbox" id="profileVisibility" defaultChecked />
              <label htmlFor="profileVisibility">Profil visible pour les autres utilisateurs</label>
            </div>
            <div className="checkbox-option">
              <input type="checkbox" id="gameActivity" defaultChecked />
              <label htmlFor="gameActivity">Partager mon activité de jeu</label>
            </div>
          </div>

          <div className="danger-zone">
            <h3>Zone de danger</h3>
            <button className="danger-btn">Désactiver mon compte</button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Profile;


