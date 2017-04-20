//
//  DetailViewController.swift
//  Instagram
//
//  Created by David Michelson on 8/13/16.
//  Copyright © 2016 David Michelson. All rights reserved.
//

import UIKit
import Parse
import ParseUI

class DetailViewController: UIViewController {

    @IBOutlet weak var postImage: PFImageView!
    @IBOutlet weak var captionLabel: UILabel!
    @IBOutlet weak var timestampLabel: UILabel!
    
    var post: PFObject?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        captionLabel.numberOfLines = 0
        
        if let caption = post!["caption"] as? String {
            captionLabel.text = caption
        } else {
            print("error caption on detail")
        }
        
        
        // Ok got it to use Parse's createdAt date by accessing post.createdAt
        if let date = post?.createdAt {
            let formatter = NSDateFormatter()
            formatter.dateStyle = NSDateFormatterStyle.LongStyle
            formatter.timeStyle = NSDateFormatterStyle.MediumStyle
            
            let dateString = formatter.stringFromDate(date)
        
            timestampLabel.text = "Posted " + dateString
        } else {
            print("error timestamp detail")
        }
        

        postImage.file = post!["media"] as! PFFile
        postImage.loadInBackground()
        
        
        print(post)

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
