import { useState, useEffect, useCallback } from 'react';

const useComments = (gameId) => {
  const [comments, setComments] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const loadComments = useCallback(async () => {
    if (!gameId) return;
    
    setLoading(true);
    setError(null);
    
    try {
      const response = await fetch(`http://localhost:8080/api/commentaires/application/${gameId}`);
      if (!response.ok) {
        throw new Error('Erreur lors du chargement des commentaires');
      }
      const data = await response.json();
      setComments(data);
    } catch (err) {
      console.error('Erreur lors du chargement des commentaires:', err);
      setError(err.message);
    } finally {
      setLoading(false);
    }
  }, [gameId]);

  useEffect(() => {
    loadComments();
  }, [loadComments]);

  const addComment = async (commentData) => {
    try {
      const response = await fetch('http://localhost:8080/api/commentaires', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(commentData)
      });

      if (!response.ok) {
        throw new Error('Erreur lors de l\'ajout du commentaire');
      }

      await loadComments(); // Recharger les commentaires
      return { success: true };
    } catch (err) {
      console.error('Erreur lors de l\'ajout:', err);
      return { success: false, error: err.message };
    }
  };

  const updateComment = async (commentId, updateData) => {
    try {
      const response = await fetch(`http://localhost:8080/api/commentaires/${commentId}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(updateData)
      });

      if (!response.ok) {
        throw new Error('Erreur lors de la modification');
      }

      await loadComments(); // Recharger les commentaires
      return { success: true };
    } catch (err) {
      console.error('Erreur lors de la modification:', err);
      return { success: false, error: err.message };
    }
  };

  const deleteComment = async (commentId, userId) => {
    try {
      const response = await fetch(`http://localhost:8080/api/commentaires/${commentId}?userId=${userId}`, {
        method: 'DELETE'
      });

      if (!response.ok) {
        throw new Error('Erreur lors de la suppression');
      }

      await loadComments(); // Recharger les commentaires
      return { success: true };
    } catch (err) {
      console.error('Erreur lors de la suppression:', err);
      return { success: false, error: err.message };
    }
  };

  const getAverageRating = () => {
    if (comments.length === 0) return 0;
    const sum = comments.reduce((acc, comment) => acc + comment.note, 0);
    return (sum / comments.length).toFixed(1);
  };

  const getCommentCount = () => comments.length;

  return {
    comments,
    loading,
    error,
    addComment,
    updateComment,
    deleteComment,
    refreshComments: loadComments,
    averageRating: getAverageRating(),
    commentCount: getCommentCount()
  };
};

export default useComments;