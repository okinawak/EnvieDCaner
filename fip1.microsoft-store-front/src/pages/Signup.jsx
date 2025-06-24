import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/App.css';

function Signup({ onSignup }) {
  const [formData, setFormData] = useState({
    profilePicture: null,
    email: '',
    username: '',
    password: '',
  });

  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value, files } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: files ? files[0] : value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const data = new FormData();
    data.append('username', formData.username);
    data.append('email', formData.email);
    data.append('password', formData.password);
    if (formData.profilePicture) {
      data.append('profilePicture', formData.profilePicture);
    }

    try {
      const response = await fetch('http://localhost:8080/api/utilisateurs/signup', {
        method: 'POST',
        body: data,
      });

      if (response.ok) {
        const result = await response.text();
        alert(result);
        if (onSignup) onSignup(formData);
        navigate('/login');
      } else {
        const errorText = await response.text();
        alert('Erreur : ' + errorText);
      }
    } catch (error) {
      alert('Erreur réseau : ' + error.message);
    }
  };


  return (
    <div className="signup-form text-center mt-5">
      <h2 className="mb-4 text-primary">Créer un compte</h2>
      <form className="profile-form" onSubmit={handleSubmit} encType="multipart/form-data">
        <div className="form-group">
          <div className="mb-3">
            <label>Photo de profil :</label><br />
            <input
              type="file"
              name="profilePicture"
              accept="image/*"
              onChange={handleChange}
              required
            />
          </div>

          <div className="mb-3">
            <label>Email :</label><br />
            <input
              type="email"
              name="email"
              value={formData.email}
              onChange={handleChange}
              required
            />
          </div>

          <div className="mb-3">
            <label>Pseudo :</label><br />
            <input
              type="text"
              name="username"
              value={formData.username}
              onChange={handleChange}
              required
            />
          </div>

          <div className="mb-3">
            <label>Mot de passe :</label><br />
            <input
              type="password"
              name="password"
              value={formData.password}
              onChange={handleChange}
              required
            />
          </div>

          <button type="submit" >S'inscrire</button>
        </div>
      </form>
    </div>
  );
}

export default Signup;
