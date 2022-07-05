using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web;
using WorkIt_Server.Models.Context;
using WorkIt_Server.Models.DTO;
using WorkIt_Server.Models.ViewModels;

namespace WorkIt_Server.BussinessLogic.Logics
{
    public class UserBussinessLogic
    {
        private WorkItDbContext db;

        public UserBussinessLogic(WorkItDbContext db)
        {
            this.Db = db;
        }

        public WorkItDbContext Db
        {
            get
            {
                return this.db;
            }
            set
            {
                this.db = value;
            }
        }

        public ProfileDetailsViewModel GetProfileDetails(int userId)
        {
            var user = db.Users.FirstOrDefault(u => u.UserId == userId);

            return new ProfileDetailsViewModel
            {
                UserId = user.UserId,
                Email = user.Email,
                FullName = user.FullName,
                Phone = user.Phone,
                NumberOfReviewsAsSupervisor = user.ReviewsAsSupervisor - 1,
                NumberOfReviewsAsTasker = user.ReviewsAsTasker - 1,
                RatingAsSupervisor = user.RaitingAsSupervisor,
                RatingAsTasker = user.RaitingAsTasker,
                PictureAsString = user.Picture
            };
        }

        public void UpdateProfile(UpdateProfileDTO updatedProfile)
        {
            var updatedUser = db.Users.FirstOrDefault(u => u.UserId == updatedProfile.UserId);

            updatedUser.Phone = updatedProfile.Phone;
            updatedUser.FullName = updatedProfile.FullName;
            updatedUser.Picture = updatedProfile.ProfilePictureAsString;

            Db.SaveChanges();
        }
    }
}