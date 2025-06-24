import React, { useState, useEffect } from 'react';
import '../styles/Creation.css';

const Creation = ({ user }) => {
  const [formData, setFormData] = useState({
    nom: '',
    description: '',
    version: '',
    note_de_mise_a_jour: '',
    prix: '',
    logo: '',
    gitUrl: '',
    gitUsername: '',
    gitPassword: '',
  });

  const [userApps, setUserApps] = useState([]);
  const [selectedAppId, setSelectedAppId] = useState('new');

  useEffect(() => {
    if (user?.id) {
      fetch(`http://localhost:8080/api/applications/auteur/${user.id}`)
        .then(res => res.json())
        .then(data => {
          console.log("Applications créées par le createur connecté :", data); // Affichage console restauré
          setUserApps(data);
        })
        .catch(err => console.error("Erreur de récupération :", err));
    }
  }, [user]);

  useEffect(() => {
    if (selectedAppId === 'new') {
      setFormData({
        nom: '',
        description: '',
        version: '',
        note_de_mise_a_jour: '',
        prix: '',
        logo: '',
        gitUrl: '',
        gitUsername: '',
        gitPassword: '',
      });
    } else {
      const selectedApp = userApps.find(app => app.id_a === Number(selectedAppId));
      if (selectedApp) {
        setFormData({
          nom: selectedApp.nom,
          description: selectedApp.description,
          version: selectedApp.version,
          note_de_mise_a_jour: selectedApp.note_de_mise_a_jour,
          prix: selectedApp.prix,
          logo: selectedApp.logo,
          gitUrl: '',
          gitUsername: '',
          gitPassword: '',
        });
      }
    }
  }, [selectedAppId, userApps]);

  const handleChange = e => {
    const { name, value, files } = e.target;
    if (name === 'logo' && files.length > 0) {
      const reader = new FileReader();
      reader.onloadend = () => {
        const base64 = reader.result.replace(/^data:image\/[a-zA-Z]+;base64,/, '');
        setFormData(prev => ({ ...prev, logo: base64 }));
      };
      reader.readAsDataURL(files[0]);
    } else {
      setFormData(prev => ({ ...prev, [name]: value }));
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const isNew = selectedAppId === 'new';
    const url = isNew
      ? 'http://localhost:8080/api/applications'
      : `http://localhost:8080/api/applications/${selectedAppId}`;
    const method = isNew ? 'POST' : 'PUT'; // Adapter si besoin

    try {
      const response = await fetch(url, {
        method,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          ...formData,
          prix: parseFloat(formData.prix),
          id_auteur: user.id,
        })
      });

      if (response.ok) {
        alert(isNew ? 'Application créée avec succès' : 'Application mise à jour');
      } else {
        alert("Erreur lors de l'envoi");
      }
    } catch (err) {
      console.error(err);
      alert("Erreur réseau");
    }
  };

  return (
    <div className="creation-container">
      <div className="creation-form-wrapper">
        <h2>{selectedAppId === 'new' ? 'Ajouter une nouvelle application' : 'Modifier une application'}</h2>

        <select value={selectedAppId} onChange={e => setSelectedAppId(e.target.value)}>
          <option value="new">➕ Nouvelle application</option>
          {userApps.map(app => (
            <option key={app.id_a} value={app.id_a}>
              {app.nom} (v{app.version})
            </option>
          ))}
        </select>

        <form className="creation-form" onSubmit={handleSubmit}>
          <div className="form-group">
            <input type="text" name="nom" placeholder="Nom" value={formData.nom} onChange={handleChange} required />
            <input type="text" name="version" placeholder="Version" value={formData.version} onChange={handleChange} required />
            <input type="number" step="0.01" name="prix" placeholder="Prix (€)" value={formData.prix} onChange={handleChange} required />
            <input type="file" name="logo" accept="image/*" onChange={handleChange} />

            <textarea name="description" placeholder="Description" value={formData.description} onChange={handleChange} required />
            <textarea name="note_de_mise_a_jour" placeholder="Notes de mise à jour" value={formData.note_de_mise_a_jour} onChange={handleChange} required />

            <h3>Informations Git (optionnel)</h3>
            <input type="text" name="gitUrl" placeholder="URL du dépôt Git" value={formData.gitUrl} onChange={handleChange} />
            <input type="text" name="gitUsername" placeholder="Nom d'utilisateur Git" value={formData.gitUsername} onChange={handleChange} />
            <input type="password" name="gitPassword" placeholder="Mot de passe Git" value={formData.gitPassword} onChange={handleChange} />

            <button type="submit">{selectedAppId === 'new' ? 'Créer' : 'Mettre à jour'}</button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default Creation;
