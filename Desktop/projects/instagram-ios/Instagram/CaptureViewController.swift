//
//  CaptureViewController.swift
//  Instagram
//
//  Created by David Michelson on 7/31/16.
//  Copyright © 2016 David Michelson. All rights reserved.
//

import UIKit
import Parse

class CaptureViewController: UIViewController, UIImagePickerControllerDelegate, UINavigationControllerDelegate {
    
    var image: UIImage?
    
    @IBOutlet weak var captionField: UITextField!
    
    @IBAction func openImagePickerController(sender: AnyObject) {
        let vc = UIImagePickerController()
        vc.delegate = self
        vc.allowsEditing = true
        vc.sourceType = UIImagePickerControllerSourceType.PhotoLibrary
        self.presentViewController(vc, animated: true, completion: nil)
    }
    
    func imagePickerController(picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [String : AnyObject]) {
        
        let originalImage = info[UIImagePickerControllerOriginalImage] as! UIImage
        let editedImage = info[UIImagePickerControllerEditedImage] as! UIImage
        
        image = editedImage
        
        dismissViewControllerAnimated(true, completion: nil)
    }
    
    
    @IBAction func submitPost(sender: AnyObject) {
        
        Post.postUserImage(image, withCaption: captionField.text, withCompletion: { (success: Bool, error: NSError?) -> Void in
            
            if success {
                print("Yay, saved a picture to cloud!")
            } else {
                print(error?.localizedDescription)
            }
        })
    }
    
    

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}

