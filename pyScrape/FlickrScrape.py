# -*- coding: utf-8 -*-
"""
Spyder Editor

This is a temporary script file.
"""

import flickrapi
import xml.etree.ElementTree as ET

method = "flickr.photosets.getPhotos"
api_key = "deb1c7b6adff4c7145b39a1d49f50f1b"
api_secret = "12d2c08b07ff04ad"
photoset_id = "72177720301006030"
user_id = "50785054@N03"

flickr = flickrapi.FlickrAPI(api_key, api_secret)

photos = flickr.photosets.getPhotos(photoset_id=photoset_id, user_id = user_id, extras="url_o")

for photo in photos.iter():
    print(photo.attrib.get('url_o'))
    print(photo.attrib.get('title'))
