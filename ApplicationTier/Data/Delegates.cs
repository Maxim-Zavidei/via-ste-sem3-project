using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using ApplicationTier.Data.Impl;
using ApplicationTier.Models;
using ApplicationTier.Pages;

namespace ApplicationTier.Data
{
    public class Delegates
    {  
        public delegate void Del();
        public Del EventChangeHandler { get; set; }

        public delegate void Del1(int id);
        public Del1 IdChangeHandler {get;set;}
    }
}