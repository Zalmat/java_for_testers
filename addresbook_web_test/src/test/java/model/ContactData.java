package model;

public record ContactData(String id, String firstname, String middlename, String lastname, String nickname, String title, String company, String address, String home, String mobile,
                          String work, String email, String email2, String email3, String homepage, String photo){


        public ContactData() {
            this("", "","","","","","","","","","","","","","", "");
        }

//        public static ContactData of(String firstname, String middlename, String lastname) {
//            return new ContactData()
//                    .withFirstname(firstname)
//                    .withMiddlename(middlename)
//                    .withLastname(lastname);
//        }

        public ContactData withFirstname(String firstname) {
            return new ContactData(this.id, firstname, this.middlename, this.lastname,this.nickname,
                    this.title,this.company,this.address,this.home,this.mobile,this.work,
                    this.email,this.email2,this.email3,this.homepage, this.photo);
        }
        public ContactData withId(String id) {
        return new ContactData(id, this.firstname, this.middlename, this.lastname,this.nickname,
                this.title,this.company,this.address,this.home,this.mobile,this.work,
                this.email,this.email2,this.email3,this.homepage, this.photo);
        }

        public ContactData withMiddlename(String middlename) {
            return new ContactData(this.id, this.firstname, middlename, this.lastname, this.nickname,
                    this.title, this.company, this.address, this.home, this.mobile,
                    this.work, this.email, this.email2, this.email3, this.homepage, this.photo);
        }

        public ContactData withLastname(String lastname) {
            return new ContactData(this.id, this.firstname, this.middlename, lastname, this.nickname,
                    this.title, this.company, this.address, this.home, this.mobile,
                    this.work, this.email, this.email2, this.email3, this.homepage, this.photo);
        }

        public ContactData withNickname(String nickname) {
            return new ContactData(this.id, this.firstname, this.middlename, this.lastname, nickname,
                    this.title, this.company, this.address, this.home, this.mobile,
                    this.work, this.email, this.email2, this.email3, this.homepage, this.photo);
        }
        public ContactData withPhoto(String photo) {
            return new ContactData(this.id, this.firstname, this.middlename, this.lastname, this.nickname,
                    this.title, this.company, this.address, this.home, this.mobile,
                    this.work, this.email, this.email2, this.email3, this.homepage, photo);
        }




    }

