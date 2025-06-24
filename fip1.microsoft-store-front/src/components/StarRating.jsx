import React from 'react';
import { Star } from 'lucide-react';

const StarRating = ({ 
  rating, 
  interactive = false, 
  onRatingChange = null, 
  size = 16,
  showNumber = false 
}) => {
  const handleStarClick = (starValue) => {
    if (interactive && onRatingChange) {
      onRatingChange(starValue);
    }
  };

  return (
    <div className="star-rating" style={{ 
      display: 'flex', 
      alignItems: 'center', 
      gap: '4px' 
    }}>
      <div className="stars" style={{ display: 'flex', gap: '2px' }}>
        {[1, 2, 3, 4, 5].map((star) => (
          <Star
            key={star}
            size={size}
            fill={star <= rating ? '#ffc107' : 'none'}
            stroke={star <= rating ? '#ffc107' : '#ccc'}
            style={{ 
              cursor: interactive ? 'pointer' : 'default',
              transition: 'all 0.2s ease'
            }}
            onClick={() => handleStarClick(star)}
            onMouseEnter={interactive ? (e) => {
              e.target.style.transform = 'scale(1.1)';
            } : undefined}
            onMouseLeave={interactive ? (e) => {
              e.target.style.transform = 'scale(1)';
            } : undefined}
          />
        ))}
      </div>
      {showNumber && (
        <span style={{ 
          fontSize: '0.9rem', 
          color: '#666',
          marginLeft: '4px'
        }}>
          ({rating}/5)
        </span>
      )}
    </div>
  );
};

export default StarRating;